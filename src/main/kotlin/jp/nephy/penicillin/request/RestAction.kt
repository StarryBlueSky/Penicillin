package jp.nephy.penicillin.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.*
import jp.nephy.penicillin.Util.Companion.logger
import jp.nephy.penicillin.model.Cursor
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.request.streaming.*
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

abstract class Action {
    abstract val requestBuilder: PenicillinRequestBuilder

    private lateinit var okHttpRequestCache: Request
    val okHttpRequest: Request
        get() {
            if (! ::okHttpRequestCache.isInitialized) {
                okHttpRequestCache = requestBuilder.build()
            }
            return okHttpRequestCache
        }
    private lateinit var okHttpResponseCache: Response
    val okHttpResponse: Response
        get() {
            if (! ::okHttpResponseCache.isInitialized) {
                repeat(requestBuilder.session.option.maxRetries) {
                    try {
                        okHttpResponseCache = requestBuilder.session.httpClient.newCall(okHttpRequest).execute()
                        return okHttpResponseCache
                    } catch (e: Exception) {
                        Util.logger.error(e) { LocalizedString.ApiRequestFailedLog.format(it + 1, requestBuilder.session.option.maxRetries) }
                        requestBuilder.session.option.retryIntervalUnit.sleep(requestBuilder.session.option.retryInterval)
                    }
                }
                throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, requestBuilder.url)
            }
            return okHttpResponseCache
        }
}

abstract class RestAction<T: Result>: Action() {
    private val defaultFallback: (PenicillinException) -> Unit = {
        logger.error(it) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

    fun queue(onSuccess: (T) -> Unit, onFailure: (PenicillinException) -> Unit) {
        requestBuilder.session.pool.execute {
            val result = try {
                complete()
            } catch (e: PenicillinException) {
                return@execute try {
                    onFailure(e)
                } catch (e: Exception) {
                    Util.logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
                }
            }

            try {
                onSuccess(result)
            } catch (e: Exception) {
                Util.logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
            }
        }
    }
    fun queue(onSuccess: (T) -> Unit) {
        queue(onSuccess, defaultFallback)
    }
    fun queue() {
        queue({  })
    }
    fun queueAfter(delay: Long, unit: TimeUnit, onSuccess: (T) -> Unit, onFailure: (PenicillinException) -> Unit) {
        requestBuilder.session.pool.schedule({
            val result = try {
                complete()
            } catch (e: PenicillinException) {
                return@schedule try {
                    onFailure(e)
                } catch (e: Exception) {
                    Util.logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
                }
            }

            try {
                onSuccess(result)
            } catch (e: Exception) {
                Util.logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
            }
        }, delay, unit)
    }
    fun queueAfter(delay: Long, unit: TimeUnit, onSuccess: (T) -> Unit) {
        queueAfter(delay, unit, onSuccess, defaultFallback)
    }
    fun queueAfter(delay: Long, unit: TimeUnit) {
        queueAfter(delay, unit, {  })
    }

    abstract fun complete(): T
    fun completeAfter(delay: Long, unit: TimeUnit): T {
        unit.sleep(delay)
        return complete()
    }

    private lateinit var contentCache: String
    val content: String
        get() {
            if (! ::contentCache.isInitialized) {
                contentCache = (okHttpResponse.body()?.string() ?: "").unescapeHTMLCharacters()
            }
            return contentCache
        }
    val jsonObject: JsonObject
        get() = try {
            JsonKt.toJsonObject(content)
        } catch (e: Exception) {
            throw PenicillinLocalizedException(LocalizedString.InvalidJsonReturned, content)
        }
    val jsonArray: JsonArray
        get() = try {
            JsonKt.toJsonArray(content)
        } catch (e: Exception) {
            throw PenicillinLocalizedException(LocalizedString.InvalidJsonReturned, content)
        }

    fun checkError() {
        if (okHttpResponse.isSuccessful) {
            return
        }

        val result = jsonObject
        if (result.contains("errors") && result["errors"].isJsonArray) {
            val error = result["errors"].jsonArray.firstOrNull() ?: throw PenicillinLocalizedException(LocalizedString.UnknownApiErrorWithStatusCode, okHttpResponse.code(), content)
            throw TwitterApiError(error["code"].nullableInt ?: -1, error["message"].nullableString.orEmpty(), content)
        } else if (result.contains("error") && result["error"].isJsonObject) {
            val error = result["error"]
            throw TwitterApiError(error["code"].nullableInt ?: -1, error["message"].nullableString.orEmpty(), content)
        } else if (result.contains("error") && result["error"].isJsonPrimitive) {
            val error = result["error"].nullableString.orEmpty()
            throw TwitterApiError(-1, error, content)
        }
    }
}

class ObjectAction<T: JsonModel>(private val model: Class<T>, override val requestBuilder: PenicillinRequestBuilder): RestAction<ObjectResult<T>>() {
    override fun complete(): ObjectResult<T> {
        checkError()

        val result = try {
            model.getConstructor(JsonObject::class.java).newInstance(jsonObject)
        } catch (e: Exception) {
            if (model == Empty::class.java) {
                @Suppress("UNCHECKED_CAST")
                model.getConstructor(JsonObject::class.java).newInstance(jsonObject()) as T
            } else {
                Util.logger.error(e) { LocalizedString.JsonParsingFailed.format(model.simpleName) }
                Util.logger.debug { content }
                throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, model.simpleName)
            }
        }
        return ObjectResult(result, model, okHttpRequest, okHttpResponse)
    }
}

class ListAction<T: JsonModel>(private val model: Class<T>, override val requestBuilder: PenicillinRequestBuilder): RestAction<ListResult<T>>() {
    override fun complete(): ListResult<T> {
        checkError()

        val result = jsonArray.map {
            try {
                model.getConstructor(JsonObject::class.java).newInstance(it.jsonObject)
            } catch (e: Exception) {
                Util.logger.error(e) { LocalizedString.JsonParsingFailed.format(model.simpleName) }
                Util.logger.debug { content }
                throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, model.simpleName)
            }
        }
        return ListResult(result, model, okHttpRequest, okHttpResponse)
    }
}

class CursorObjectAction<T: Cursor>(private val model: Class<T>, override val requestBuilder: PenicillinRequestBuilder): RestAction<CursorObjectResult<T>>() {
    override fun complete(): CursorObjectResult<T> {
        checkError()

        val result = try {
            model.getConstructor(JsonObject::class.java).newInstance(jsonObject)
        } catch (e: Exception) {
            Util.logger.error(e) { LocalizedString.JsonParsingFailed.format(model.simpleName) }
            Util.logger.debug { content }
            throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, model.simpleName)
        }
        return CursorObjectResult(result, model, requestBuilder, okHttpRequest, okHttpResponse)
    }
}

class TextAction(override val requestBuilder: PenicillinRequestBuilder): RestAction<TextResult>() {
    override fun complete(): TextResult {
        checkError()
        return TextResult(content, okHttpRequest, okHttpResponse)
    }
}

class StreamAction<T: StreamListener>(override val requestBuilder: PenicillinRequestBuilder): Action() {
    val result = StreamResult(okHttpRequest, okHttpResponse)

    @Suppress("UNCHECKED_CAST")
    fun listen(listener: T) = when (listener) {
        is UserStreamListener -> UserStream(this as StreamAction<UserStreamListener>, listener)
        is SampleStreamListener -> SampleStream(this as StreamAction<SampleStreamListener>, listener)
        is FilterStreamListener -> FilterStream(this as StreamAction<FilterStreamListener>, listener)
        is LivePipelineListener -> LivePipeline(this as StreamAction<LivePipelineListener>, listener)
        else -> throw IllegalArgumentException()
    }
}

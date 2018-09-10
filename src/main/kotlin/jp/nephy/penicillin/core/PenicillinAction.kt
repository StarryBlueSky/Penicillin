package jp.nephy.penicillin.core

import com.google.gson.JsonObject
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.isSuccess
import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.streaming.StreamHandler
import jp.nephy.penicillin.core.streaming.StreamListener
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinCursorModel
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.experimental.*
import kotlinx.io.charsets.MalformedInputException
import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

private val logger = KotlinLogging.logger("Penicillin.ApiAction")

interface PenicillinAction {
    val request: PenicillinRequest
}

private interface JsonRequest<M: PenicillinModel> {
    val model: Class<M>
}

abstract class ApiAction<R> {
    private val defaultCallback: (response: R) -> Unit = { }
    private val defaultFallback: (e: Throwable) -> Unit = { e ->
        logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

    @Throws(PenicillinException::class)
    abstract suspend fun await(): R

    @Throws(PenicillinException::class)
    suspend fun awaitWithTimeout(timeout: Long, unit: TimeUnit): R? {
        return withTimeoutOrNull(timeout, unit) {
            await()
        }
    }

    @Throws(PenicillinException::class)
    fun complete(): R {
        return runBlocking {
            await()
        }
    }

    @Throws(PenicillinException::class)
    fun completeWithTimeout(timeout: Long, unit: TimeUnit): R? {
        return runBlocking {
            awaitWithTimeout(timeout, unit)
        }
    }

    @Throws(PenicillinException::class)
    fun async(context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT): Deferred<R> {
        return async(context, start) {
            await()
        }
    }

    @Throws(PenicillinException::class)
    fun asyncWithTimeout(timeout: Long, unit: TimeUnit, context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT): Deferred<R?> {
        return async(context, start) {
            awaitWithTimeout(timeout, unit)
        }
    }

    fun queue(context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT, onSuccess: (response: R) -> Unit, onFailure: (e: Throwable) -> Unit): Job {
        return launch(context, start) {
            try {
                complete().let(onSuccess)
            } catch (e: Exception) {
                try {
                    onFailure(e)
                } catch (e: Exception) {
                    defaultFallback(e)
                }
            }
        }
    }

    fun queue(context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT, onSuccess: (response: R) -> Unit): Job {
        return queue(context, start, onSuccess, defaultFallback)
    }

    fun queue(context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT): Job {
        return queue(context, start, defaultCallback, defaultFallback)
    }

    fun queueWithTimeout(timeout: Long, unit: TimeUnit, context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT, onSuccess: (response: R) -> Unit, onFailure: (e: Throwable) -> Unit): Job {
        return launch(context, start) {
            try {
                withTimeout(timeout, unit) {
                    complete()
                }.let(onSuccess)
            } catch (e: Exception) {
                try {
                    onFailure(e)
                } catch (e: Exception) {
                    defaultFallback(e)
                }
            }
        }
    }

    fun queueWithTimeout(timeout: Long, unit: TimeUnit, context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT, onSuccess: (response: R) -> Unit): Job {
        return queueWithTimeout(timeout, unit, context, start, onSuccess, defaultFallback)
    }

    fun queueWithTimeout(timeout: Long, unit: TimeUnit, context: CoroutineContext = DefaultDispatcher, start: CoroutineStart = CoroutineStart.DEFAULT): Job {
        return queueWithTimeout(timeout, unit, context, start, defaultCallback, defaultFallback)
    }
}

private suspend fun executeRequest(session: Session, request: PenicillinRequest): Pair<HttpRequest, HttpResponse> {
    repeat(session.option.maxRetries) {
        try {
            val response = session.httpClient.request<HttpResponse>(request.builder.finalize())
            return response.call.request to response
        } catch (e: Exception) {
            // TEMP FIX: Set-Cookie header format may be invalid like Sat, 5 Sep 2020 16:30:05 GMT
            if (e is IllegalStateException && e.message.orEmpty().startsWith("Invalid date length.")) {
                logger.debug(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            } else {
                logger.error(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            }
        }

        delay(session.option.retryInterval, session.option.retryIntervalUnit)
    }

    throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, args = *arrayOf(request.builder.url))
}

private suspend fun HttpResponse.readTextSafe(): String {
    return try {
        readText().trim().unescapeHTML()
    } catch (e: MalformedInputException) {
        ""
    }
}

internal fun String.unescapeHTML(): String {
    return replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
}

private fun checkError(request: HttpRequest, response: HttpResponse, content: String) {
    logger.trace {
        buildString {
            appendln("${response.version} ${response.status.value} ${request.method.value} ${request.url}")

            val (requestHeaders, responseHeaders) = request.headers.flattenEntries() to response.headers.flattenEntries()
            val (longestRequestHeaderLength, longestResponseHeaderLength) = requestHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1 to responseHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1
            appendln("Request headers =\n${requestHeaders.joinToString("\n") { "    ${it.first.padEnd(longestRequestHeaderLength)}: ${it.second}" }}")
            appendln("Response headers =\n${responseHeaders.joinToString("\n") { "    ${it.first.padEnd(longestResponseHeaderLength)}: ${it.second}" }}\n")

            append(if (!content.isBlank()) content else "(Empty Response)")
        }
    }

    val json = try {
        content.toJsonObject()
    } catch (e: Exception) {
        return
    }

    if (response.status.isSuccess()) {
        return
    }

    if (json.contains("errors") && json["errors"].isJsonArray) {
        val error = json["errors"].jsonArray.firstOrNull()
                ?: throw PenicillinLocalizedException(LocalizedString.UnknownApiErrorWithStatusCode, args = *arrayOf(response.status.value, content))
        throw TwitterApiError(error["code"].toIntOrDefault(-1), error["message"].toStringOrDefault(""), content, request, response)
    } else if (json.contains("error") && json["error"].isJsonObject) {
        val error = json["error"]
        throw TwitterApiError(error["code"].toIntOrDefault(-1), error["message"].toStringOrDefault(""), content, request, response)
    } else if (json.contains("error") && json["error"].isJsonPrimitive) {
        val error = json["error"].toStringOrDefault("")
        throw TwitterApiError(-1, error, content, request, response)
    }
}

class PenicillinJsonObjectAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinJsonObjectResponse<M>>() {
    override suspend fun await(): PenicillinJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = try {
            content.toJsonObject()
        } catch (e: Exception) {
            if (model == Empty::class.java) {
                jsonObject()
            } else {
                logger.error(e) { LocalizedString.JsonParsingFailed.format(content) }
                throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, content)
            }
        }
        val result = try {
            model.getConstructor(JsonObject::class.java).newInstance(json)
        } catch (e: Exception) {
            logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
            throw PenicillinLocalizedException(LocalizedString.JsonModelCastFailed, args = *arrayOf(model.simpleName, content))
        }

        return PenicillinJsonObjectResponse(model, result, request, response, content, this)
    }
}

class PenicillinJsonArrayAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinJsonArrayResponse<M>>() {
    override suspend fun await(): PenicillinJsonArrayResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = try {
            content.toJsonArray()
        } catch (e: Exception) {
            logger.error(e) { LocalizedString.JsonParsingFailed.format(content) }
            throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, content)
        }

        return PenicillinJsonArrayResponse(model, request, response, content, this).apply {
            for (it in json) {
                val result = try {
                    model.getConstructor(JsonObject::class.java).newInstance(it.nullableJsonObject ?: continue)
                } catch (e: Exception) {
                    logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
                    throw PenicillinLocalizedException(LocalizedString.JsonModelCastFailed, args = *arrayOf(model.simpleName, content))
                }
                add(result)
            }
        }
    }
}

class PenicillinCursorJsonObjectAction<M: PenicillinCursorModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinCursorJsonObjectResponse<M>>() {
    override suspend fun await(): PenicillinCursorJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = try {
            content.toJsonObject()
        } catch (e: Exception) {
            logger.error(e) { LocalizedString.JsonParsingFailed.format(content) }
            throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, content)
        }
        val result = try {
            model.getConstructor(JsonObject::class.java).newInstance(json)
        } catch (e: Exception) {
            logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
            throw PenicillinLocalizedException(LocalizedString.JsonModelCastFailed, args = *arrayOf(model.simpleName, content))
        }

        return PenicillinCursorJsonObjectResponse(model, result, request, response, content, this)
    }
}

class PenicillinTextAction(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinTextResponse>() {
    override suspend fun await(): PenicillinTextResponse {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        return PenicillinTextResponse(request, response, content, this)
    }
}

class PenicillinStreamAction<L: StreamListener, H: StreamHandler<L>>(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinStreamResponse<L, H>>() {
    override suspend fun await(): PenicillinStreamResponse<L, H> {
        val (request, response) = executeRequest(request.session, request)

        if (!response.status.isSuccess()) {
            throw PenicillinLocalizedException(LocalizedString.StreamingApiRequestFailed, request, response, response.status.value, response.status.description)
        }

        return PenicillinStreamResponse(request, response, this)
    }
}

private typealias JsonObjectActionCallback<M> = (results: PenicillinMultipleJsonObjectActions.Results<M>) -> PenicillinJsonObjectAction<*>

class PenicillinMultipleJsonObjectActions<M: PenicillinModel>(val first: PenicillinJsonObjectAction<M>, private val requests: List<JsonObjectActionCallback<M>>): ApiAction<List<PenicillinJsonObjectResponse<*>>>() {
    class Builder<M: PenicillinModel>(private val first: () -> PenicillinJsonObjectAction<M>) {
        private val requests = mutableListOf<JsonObjectActionCallback<M>>()
        fun request(callback: JsonObjectActionCallback<M>) = apply {
            requests.add(callback)
        }

        internal fun build(): PenicillinMultipleJsonObjectActions<M> {
            return PenicillinMultipleJsonObjectActions(first(), requests)
        }
    }

    class Results<M: PenicillinModel>(val first: PenicillinJsonObjectResponse<M>, val responses: Map<Class<out PenicillinModel>, List<PenicillinJsonObjectResponse<*>>>) {
        inline fun <reified T: PenicillinModel> responses(): List<PenicillinJsonObjectResponse<T>> {
            @Suppress("UNCHECKED_CAST")
            return responses[T::class.java].orEmpty().map { it as PenicillinJsonObjectResponse<T> }
        }
    }

    override suspend fun await(): List<PenicillinJsonObjectResponse<*>> {
        val first = first.await()
        val responses = mutableMapOf<Class<out PenicillinModel>, MutableList<PenicillinJsonObjectResponse<*>>>()
        val responsesList = mutableListOf<PenicillinJsonObjectResponse<*>>()
        for (request in requests) {
            val results = Results(first, responses)
            val result = request.invoke(results).await()

            if (result.model in responses) {
                responses[result.model]!!.add(result)
            } else {
                responses[result.model] = mutableListOf(result)
            }
            responsesList += result
        }

        return responsesList
    }

    operator fun plus(callback: JsonObjectActionCallback<M>): PenicillinMultipleJsonObjectActions<M> {
        return PenicillinMultipleJsonObjectActions.Builder {
            first
        }.also { builder ->
            requests.forEach {
                builder.request(it)
            }
        }.request(callback).build()
    }
}

private typealias JoinedJsonObjectActionCallback<M> = (results: List<List<PenicillinJsonObjectResponse<*>>>) -> PenicillinJsonObjectAction<M>

class PenicillinJoinedJsonObjectActions<M: PenicillinModel, T: PenicillinModel>(private val actions: List<PenicillinMultipleJsonObjectActions<M>>, private val finalizer: JoinedJsonObjectActionCallback<T>): ApiAction<PenicillinJsonObjectResponse<T>>() {
    override suspend fun await(): PenicillinJsonObjectResponse<T> {
        return finalizer(actions.map { it.await() }).await()
    }
}

fun <M: PenicillinModel, T: PenicillinModel> List<PenicillinMultipleJsonObjectActions<M>>.join(finalizer: JoinedJsonObjectActionCallback<T>): PenicillinJoinedJsonObjectActions<M, T> {
    return PenicillinJoinedJsonObjectActions(this, finalizer)
}

inline fun <reified M: PenicillinModel> List<PenicillinJsonObjectResponse<*>>.filter(): List<PenicillinJsonObjectResponse<M>> {
    @Suppress("UNCHECKED_CAST")
    return filter { it.model == M::class.java }.map { it as PenicillinJsonObjectResponse<M> }
}

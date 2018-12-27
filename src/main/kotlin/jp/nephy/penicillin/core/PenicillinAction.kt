@file:Suppress("UNUSED")

package jp.nephy.penicillin.core

import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.isSuccess
import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.byInt
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.streaming.StreamHandler
import jp.nephy.penicillin.core.streaming.StreamListener
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinCursorModel
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

private val logger = KotlinLogging.logger("Penicillin.ApiAction")

interface PenicillinAction {
    val request: PenicillinRequest
}

private interface JsonRequest<M: PenicillinModel> {
    val model: KClass<M>
}

private typealias ApiCallback<R> = suspend (response: R) -> Unit
private typealias ApiFallback = suspend (e: Throwable) -> Unit

abstract class ApiAction<R>(private val session: Session) {
    private val defaultCallback: ApiCallback<R> = { }
    private val defaultFallback: ApiFallback = { e ->
        logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

    @Throws(PenicillinException::class, CancellationException::class)
    abstract suspend fun await(): R

    @Throws(PenicillinException::class, CancellationException::class)
    suspend fun awaitWithTimeout(timeout: Long, unit: TimeUnit): R? {
        return withTimeoutOrNull(unit.toMillis(timeout)) {
            await()
        }
    }

    @Throws(PenicillinException::class)
    fun complete(): R {
        return runBlocking(session.coroutineContext) {
            await()
        }
    }

    @Throws(PenicillinException::class)
    fun completeWithTimeout(timeout: Long, unit: TimeUnit): R? {
        return runBlocking(session.coroutineContext) {
            awaitWithTimeout(timeout, unit)
        }
    }

    fun queue(onFailure: ApiFallback = defaultFallback, onSuccess: ApiCallback<R> = defaultCallback): Job {
        return session.launch {
            runCatching {
                await()
            }.onSuccess {
                onSuccess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }

    fun queueWithTimeout(timeout: Long, unit: TimeUnit, onFailure: ApiFallback = defaultFallback, onSuccess: ApiCallback<R> = defaultCallback): Job {
        return session.launch {
            runCatching {
                withTimeout(unit.toMillis(timeout)) {
                    await()
                }
            }.onSuccess {
                onSuccess.invoke(it)
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }
}

private suspend fun executeRequest(session: Session, request: PenicillinRequest): Pair<HttpRequest, HttpResponse> {
    lateinit var lastException: Throwable

    repeat(session.option.maxRetries) {
        try {
            val response = session.httpClient.request<HttpResponse>(request.builder.finalize())
            return response.call.request to response
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            // TEMP FIX: Set-CookieConfig header format may be invalid like Sat, 5 Sep 2020 16:30:05 GMT
            if (e is IllegalStateException && e.message.orEmpty().startsWith("Invalid date length.")) {
                logger.debug(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            } else {
                logger.error(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            }

            lastException = e
        }

        if (it < session.option.maxRetries) {
            delay(session.option.retryInMillis)
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, cause = lastException, args = *arrayOf(request.builder.url))
}

private suspend fun HttpResponse.readTextSafe(): String? {
    val maxRetries = 3

    repeat(maxRetries) {
        try {
            return readText().trim().unescapeHTML()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            logger.error(e) { "Failed to read text. (${it + 1}/$maxRetries)\n${call.request.url}" }
        }
    }

    return null
}

internal fun String.unescapeHTML(): String {
    return replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
}

private fun String.toJsonObjectSafe(ignoreError: Boolean = false): JsonObject? {
    return try {
        toJsonObject()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        if (ignoreError) {
            return jsonObjectOf()
        }

        logger.error(e) { LocalizedString.JsonParsingFailed.format(this) }
        null
    }
}

private fun String.toJsonArraySafe(ignoreError: Boolean = false): JsonArray? {
    return try {
        toJsonArray()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        if (ignoreError) {
            return jsonArrayOf()
        }

        logger.error(e) { LocalizedString.JsonParsingFailed.format(this) }
        null
    }
}

private fun <T: PenicillinModel> JsonObject.parseSafe(model: KClass<T>, content: String?): T? {
    return try {
        parse(model)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
        null
    }
}

private fun <T: PenicillinModel> JsonArray.parseSafe(model: KClass<T>, content: String?): List<T> {
    return try {
        parseList(model)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
        emptyList()
    }
}

private fun checkError(request: HttpRequest, response: HttpResponse, content: String? = null) {
    logger.trace {
        buildString {
            appendln("${response.version} ${response.status.value} ${request.method.value} ${request.url}")

            val (requestHeaders, responseHeaders) = request.headers.flattenEntries() to response.headers.flattenEntries()
            val (longestRequestHeaderLength, longestResponseHeaderLength) = requestHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1 to responseHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1
            appendln("Request headers =\n${requestHeaders.joinToString("\n") { "    ${it.first.padEnd(longestRequestHeaderLength)}: ${it.second}" }}")
            appendln("Response headers =\n${responseHeaders.joinToString("\n") { "    ${it.first.padEnd(longestResponseHeaderLength)}: ${it.second}" }}\n")

            append(
                when {
                    content == null -> {
                        "(Streaming Response)"
                    }
                    content.isBlank() -> {
                        "(Empty Response)"
                    }
                    else -> {
                        content
                    }
                }
            )
        }
    }

    if (response.status.isSuccess()) {
        return
    }

    val json = content?.toJsonObjectSafe(true)
    if (json != null) {
        val error = json.getOrNull("errors")?.jsonArrayOrNull?.firstOrNull() ?: json.getOrNull("error")
        when (error) {
            is JsonObject -> {
                val code by error.byInt { -1 }
                val message by error.byString { "" }
                throw TwitterApiError(code, message, content, request, response)
            }
            is JsonPrimitive -> {
                throw TwitterApiError(-1, error.content, content, request, response)
            }
            else -> {
                throw PenicillinLocalizedException(LocalizedString.UnknownApiErrorWithStatusCode, request, response, null, response.status.value, content)
            }
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiReturnedNon200StatusCode, request, response, null, response.status.value, response.status.description)
}

class PenicillinJsonObjectAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: KClass<M>): PenicillinAction, JsonRequest<M>,
    ApiAction<PenicillinJsonObjectResponse<M>>(request.session) {
    override suspend fun await(): PenicillinJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonObjectSafe(model == Empty::class) ?: throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, null, content)
        val result = json.parseSafe(model, content) ?: throw PenicillinLocalizedException(LocalizedString.JsonModelCastFailed, request, response, null, model.simpleName, content)

        return PenicillinJsonObjectResponse(model, result, request, response, content.orEmpty(), this)
    }
}

class PenicillinJsonArrayAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: KClass<M>): PenicillinAction, JsonRequest<M>,
    ApiAction<PenicillinJsonArrayResponse<M>>(request.session) {
    override suspend fun await(): PenicillinJsonArrayResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonArraySafe() ?: throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, null, content)

        return PenicillinJsonArrayResponse(model, request, response, content, this).apply {
            addAll(json.parseSafe(model, content))
        }
    }
}

class PenicillinCursorJsonObjectAction<M: PenicillinCursorModel>(override val request: PenicillinRequest, override val model: KClass<M>): PenicillinAction, JsonRequest<M>,
    ApiAction<PenicillinCursorJsonObjectResponse<M>>(request.session) {
    override suspend fun await(): PenicillinCursorJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonObjectSafe() ?: throw PenicillinLocalizedException(LocalizedString.JsonParsingFailed, request, response, null, content)
        val result = json.parseSafe(model, content) ?: throw PenicillinLocalizedException(LocalizedString.JsonModelCastFailed, request, response, null, model.simpleName, content)

        return PenicillinCursorJsonObjectResponse(model, result, request, response, content, this)
    }

    // TODO
    fun untilLast() = sequence {
        val first = complete()
        yield(first)
        var cursor = first.result.nextCursor
        while (cursor != 0L) {
            val result = try {
                first.byCursor(cursor).complete()
            } catch (e: PenicillinException) {
                break
            }

            yield(result)
            cursor = result.result.nextCursor
        }
    }
}

class PenicillinTextAction(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinTextResponse>(request.session) {
    override suspend fun await(): PenicillinTextResponse {
        val (request, response) = executeRequest(request.session, request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        return PenicillinTextResponse(request, response, content.orEmpty(), this)
    }
}

class PenicillinStreamAction<L: StreamListener, H: StreamHandler<L>>(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinStreamResponse<L, H>>(request.session) {
    override suspend fun await(): PenicillinStreamResponse<L, H> {
        val (request, response) = executeRequest(request.session, request)
        checkError(request, response)

        return PenicillinStreamResponse(request, response, this)
    }
}

private typealias JsonObjectActionCallback<M> = suspend (results: PenicillinMultipleJsonObjectActions.Results<M>) -> PenicillinJsonObjectAction<*>

class PenicillinMultipleJsonObjectActions<M: PenicillinModel>(val first: PenicillinJsonObjectAction<M>, private val requests: List<JsonObjectActionCallback<M>>):
    ApiAction<List<PenicillinJsonObjectResponse<*>>>(first.request.session) {
    class Builder<M: PenicillinModel>(private val first: () -> PenicillinJsonObjectAction<M>) {
        private val requests = mutableListOf<JsonObjectActionCallback<M>>()
        fun request(callback: JsonObjectActionCallback<M>) = apply {
            requests.add(callback)
        }

        internal fun build(): PenicillinMultipleJsonObjectActions<M> {
            return PenicillinMultipleJsonObjectActions(first(), requests)
        }
    }

    class Results<M: PenicillinModel>(val first: PenicillinJsonObjectResponse<M>, val responses: Map<KClass<out PenicillinModel>, List<PenicillinJsonObjectResponse<*>>>) {
        inline fun <reified T: PenicillinModel> responses(): List<PenicillinJsonObjectResponse<T>> {
            @Suppress("UNCHECKED_CAST") return responses[T::class].orEmpty().map { it as PenicillinJsonObjectResponse<T> }
        }
    }

    override suspend fun await(): List<PenicillinJsonObjectResponse<*>> {
        val first = first.await()
        val responses = mutableMapOf<KClass<out PenicillinModel>, MutableList<PenicillinJsonObjectResponse<*>>>()
        val responsesList = mutableListOf<PenicillinJsonObjectResponse<*>>()
        for (request in requests) {
            val result = runCatching {
                request.invoke(Results(first, responses)).await()
            }.getOrNull() ?: continue

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

class PenicillinJoinedJsonObjectActions<M: PenicillinModel, T: PenicillinModel>(
    private val actions: List<PenicillinMultipleJsonObjectActions<M>>, private val finalizer: JoinedJsonObjectActionCallback<T>
): ApiAction<PenicillinJsonObjectResponse<T>>(actions.first().first.request.session) {
    override suspend fun await(): PenicillinJsonObjectResponse<T> {
        return finalizer(actions.map { it.await() }).await()
    }
}

fun <M: PenicillinModel, T: PenicillinModel> List<PenicillinMultipleJsonObjectActions<M>>.join(finalizer: JoinedJsonObjectActionCallback<T>): PenicillinJoinedJsonObjectActions<M, T> {
    return PenicillinJoinedJsonObjectActions(this, finalizer)
}

inline fun <reified M: PenicillinModel> List<PenicillinJsonObjectResponse<*>>.filter(): List<PenicillinJsonObjectResponse<M>> {
    @Suppress("UNCHECKED_CAST") return filter { it.model == M::class }.map { it as PenicillinJsonObjectResponse<M> }
}

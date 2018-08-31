package jp.nephy.penicillin.core

import com.google.gson.JsonObject
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.isSuccess
import io.ktor.util.toMap
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.streaming.StreamHandler
import jp.nephy.penicillin.core.streaming.StreamListener
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinCursorModel
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.io.charsets.MalformedInputException
import mu.KotlinLogging
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger("Penicillin.ApiAction")

interface PenicillinAction {
    val request: PenicillinRequest
}

private interface JsonRequest<M: PenicillinModel> {
    val model: Class<M>
}

abstract class ApiAction<R>(private val executor: ExecutorService) {
    private val defaultCallback: (response: R) -> Unit = { }
    private val defaultFallback: (e: Exception) -> Unit = { e ->
        logger.error(e) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

    abstract fun complete(): R

    fun completeAfter(delay: Long, unit: TimeUnit): R {
        unit.sleep(delay)
        return complete()
    }

    fun execute(onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit) {
        try {
            complete().let(onSuccess)
        } catch (e: Exception) {
            try {
                e.let(onFailure)
            } catch (e: Exception) {
                e.let(defaultFallback)
            }
        }
    }

    fun execute(onSuccess: (response: R) -> Unit) {
        execute(onSuccess, defaultFallback)
    }

    fun execute() {
        execute(defaultCallback, defaultFallback)
    }

    fun executeAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit) {
        unit.sleep(delay)
        execute(onSuccess, onFailure)
    }

    fun executeAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit) {
        executeAfter(delay, unit, onSuccess, defaultFallback)
    }

    fun executeAfter(delay: Long, unit: TimeUnit) {
        executeAfter(delay, unit, defaultCallback, defaultFallback)
    }

    fun submit(onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit): Future<*> {
        return executor.submit {
            execute(onSuccess, onFailure)
        }
    }

    fun submit(onSuccess: (response: R) -> Unit): Future<*> {
        return submit(onSuccess, defaultFallback)
    }

    fun submit(): Future<*> {
        return submit(defaultCallback, defaultFallback)
    }

    fun submitAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit): Future<*> {
        return executor.submit {
            executeAfter(delay, unit, onSuccess, onFailure)
        }
    }

    fun submitAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit): Future<*> {
        return submitAfter(delay, unit, onSuccess, defaultFallback)
    }

    fun submitAfter(delay: Long, unit: TimeUnit): Future<*> {
        return submitAfter(delay, unit, defaultCallback, defaultFallback)
    }

    fun queue(onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit) {
        executor.execute {
            execute(onSuccess, onFailure)
        }
    }

    fun queue(onSuccess: (response: R) -> Unit) {
        queue(onSuccess, defaultFallback)
    }

    fun queue() {
        queue(defaultCallback, defaultFallback)
    }

    fun queueAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit, onFailure: (e: Exception) -> Unit) {
        executor.submit {
            executeAfter(delay, unit, onSuccess, onFailure)
        }
    }

    fun queueAfter(delay: Long, unit: TimeUnit, onSuccess: (response: R) -> Unit) {
        queueAfter(delay, unit, onSuccess, defaultFallback)
    }

    fun queueAfter(delay: Long, unit: TimeUnit) {
        queueAfter(delay, unit, defaultCallback, defaultFallback)
    }
}

private fun executeRequest(session: Session, request: PenicillinRequest): Pair<HttpRequest, HttpResponse> {
    repeat(session.option.maxRetries) {
        try {
            val response = runBlocking {
                session.httpClient.request<HttpResponse>(request.builder.finalize())
            }
            logger.trace { "${response.version} ${response.status.value} ${response.call.request.method.value} ${response.call.request.url}" }
            return response.call.request to response
        } catch (e: Exception) {
            logger.error(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
        }

        session.option.retryIntervalUnit.sleep(session.option.retryInterval)
    }

    throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, args = *arrayOf(request.builder.url))
}

private val HttpResponse.textContent: String
    get() = runBlocking {
        try {
            readText()
        } catch (e: MalformedInputException) {
            ""
        }
    }

private fun checkError(request: HttpRequest, response: HttpResponse, content: String) {
    logger.trace { "Request headers = ${request.headers.toMap()}" }
    logger.trace { "Response headers = ${response.headers.toMap()}" }
    logger.trace { content }

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

class PenicillinJsonObjectAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinJsonObjectResponse<M>>(request.session.executor) {
    override fun complete(): PenicillinJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.textContent
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

class PenicillinJsonArrayAction<M: PenicillinModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinJsonArrayResponse<M>>(request.session.executor) {
    override fun complete(): PenicillinJsonArrayResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.textContent
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

class PenicillinCursorJsonObjectAction<M: PenicillinCursorModel>(override val request: PenicillinRequest, override val model: Class<M>): PenicillinAction, JsonRequest<M>, ApiAction<PenicillinCursorJsonObjectResponse<M>>(request.session.executor) {
    override fun complete(): PenicillinCursorJsonObjectResponse<M> {
        val (request, response) = executeRequest(request.session, request)
        val content = response.textContent
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

class PenicillinTextAction(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinTextResponse>(request.session.executor) {
    override fun complete(): PenicillinTextResponse {
        val (request, response) = executeRequest(request.session, request)
        val content = response.textContent
        checkError(request, response, content)

        return PenicillinTextResponse(request, response, content, this)
    }
}

class PenicillinStreamAction<L: StreamListener, H: StreamHandler<L>>(override val request: PenicillinRequest): PenicillinAction, ApiAction<PenicillinStreamResponse<L, H>>(request.session.executor) {
    override fun complete(): PenicillinStreamResponse<L, H> {
        val (request, response) = executeRequest(request.session, request)

        return PenicillinStreamResponse(request, response, this)
    }
}

private typealias JsonObjectActionCallback<M> = (results: PenicillinMultipleJsonObjectActions.Results<M>) -> PenicillinJsonObjectAction<*>

class PenicillinMultipleJsonObjectActions<M: PenicillinModel>(val first: PenicillinJsonObjectAction<M>, private val requests: List<JsonObjectActionCallback<M>>): ApiAction<List<PenicillinJsonObjectResponse<*>>>(first.request.session.executor) {
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

    override fun complete(): List<PenicillinJsonObjectResponse<*>> {
        val first = first.complete()
        val responses = mutableMapOf<Class<out PenicillinModel>, MutableList<PenicillinJsonObjectResponse<*>>>()
        val responsesList = mutableListOf<PenicillinJsonObjectResponse<*>>()
        for (request in requests) {
            val results = Results(first, responses)
            val result = request.invoke(results).complete()

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

class PenicillinJoinedJsonObjectActions<M: PenicillinModel, T: PenicillinModel>(private val actions: List<PenicillinMultipleJsonObjectActions<M>>, private val finalizer: JoinedJsonObjectActionCallback<T>): ApiAction<PenicillinJsonObjectResponse<T>>(actions.first().first.request.session.executor) {
    override fun complete(): PenicillinJsonObjectResponse<T> {
        return finalizer(actions.map { it.complete() }).complete()
    }
}

fun <M: PenicillinModel, T: PenicillinModel> List<PenicillinMultipleJsonObjectActions<M>>.join(finalizer: JoinedJsonObjectActionCallback<T>): PenicillinJoinedJsonObjectActions<M, T> {
    return PenicillinJoinedJsonObjectActions(this, finalizer)
}

inline fun <reified M: PenicillinModel> List<PenicillinJsonObjectResponse<*>>.filter(): List<PenicillinJsonObjectResponse<M>> {
    @Suppress("UNCHECKED_CAST")
    return filter { it.model == M::class.java }.map { it as PenicillinJsonObjectResponse<M> }
}

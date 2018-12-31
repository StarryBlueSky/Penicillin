@file:Suppress("UNUSED")

package jp.nephy.penicillin.core

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import io.ktor.http.Headers
import jp.nephy.jsonkt.JsonArray
import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.jsonArrayOf
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.streaming.*
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.special.AccessLevel
import jp.nephy.penicillin.models.special.RateLimit
import java.io.Closeable
import kotlin.reflect.KClass

interface PenicillinResponse: Closeable {
    val request: HttpRequest
    val response: HttpResponse
    val action: PenicillinAction
    val headers: ResponseHeaders

    override fun close() {
        response.close()
    }
}

class ResponseHeaders(private val headers: Headers) {
    val responseTimeMs by lazy {
        headers["x-response-time"]?.toIntOrNull()
    }
    val accessLevel by lazy {
        AccessLevel.getLevel(headers["x-access-level"])
    }
    val rateLimit by lazy {
        RateLimit(headers["x-rate-limit-limit"], headers["x-rate-limit-remaining"], headers["x-rate-limit-reset"])
    }
}

private interface JsonResponse<M: PenicillinModel, T: Any> {
    val model: KClass<M>
    val json: T
}

private interface CompletedResponse {
    val content: String
}

data class PenicillinJsonObjectResponse<M: PenicillinModel>(
    override val model: KClass<M>, val result: M, override val request: HttpRequest, override val response: HttpResponse, override val content: String, override val action: PenicillinAction
): PenicillinResponse, JsonResponse<M, JsonObject>, CompletedResponse {
    override val json by lazy { result.json }
    override val headers by lazy { ResponseHeaders(response.headers) }
}

data class PenicillinJsonArrayResponse<M: PenicillinModel>(
    override val model: KClass<M>, override val request: HttpRequest, override val response: HttpResponse, override val content: String, override val action: PenicillinAction
): PenicillinResponse, JsonResponse<M, JsonArray>, CompletedResponse, ArrayList<M>() {
    override val json by lazy { jsonArrayOf(*map { it.json }.toTypedArray()) }
    override val headers by lazy { ResponseHeaders(response.headers) }
}

data class PenicillinCursorJsonObjectResponse<M: PenicillinCursorModel>(
    override val model: KClass<M>, val result: M, override val request: HttpRequest, override val response: HttpResponse, override val content: String, override val action: PenicillinAction
): PenicillinResponse, JsonResponse<M, JsonObject>, CompletedResponse {
    override val json = result.json
    override val headers = ResponseHeaders(response.headers)

    val nextCursor: Long
        get() = result.nextCursor
    fun hasNext(): Boolean {
        return nextCursor > 0
    }
    fun next() = byCursor(nextCursor)

    val previousCursor: Long
        get() = result.previousCursor
    fun hasPrevious(): Boolean {
        return previousCursor > 0
    }
    fun previous() = byCursor(previousCursor)

    fun untilLast() = sequence {
        yield(this@PenicillinCursorJsonObjectResponse)
        yieldAll(next().untilLast())
    }

    fun byCursor(cursor: Long): PenicillinCursorJsonObjectAction<M> {
        if (cursor == 0L) {
            throw PenicillinLocalizedException(LocalizedString.CursorIsZero, request, response)
        }

        action.request.builder.parameter("cursor" to cursor)

        return PenicillinCursorJsonObjectAction(action.request, model)
    }
}

val Sequence<PenicillinCursorJsonObjectResponse<CursorIds>>.allIds
    get() = toList().flatMap { it.result.ids }
val Sequence<PenicillinCursorJsonObjectResponse<CursorLists>>.allLists
    get() = toList().flatMap { it.result.lists }
val Sequence<PenicillinCursorJsonObjectResponse<CursorUsers>>.allUsers
    get() = toList().flatMap { it.result.users }

data class PenicillinTextResponse(override val request: HttpRequest, override val response: HttpResponse, override val content: String, override val action: PenicillinAction): PenicillinResponse,
    CompletedResponse {
    override val headers = ResponseHeaders(response.headers)
}

data class PenicillinStreamResponse<L: StreamListener, H: StreamHandler<L>>(override val request: HttpRequest, override val response: HttpResponse, override val action: PenicillinAction):
    PenicillinResponse {
    override val headers = ResponseHeaders(response.headers)

    fun listen(listener: L): StreamProcessor<L, H> {
        @Suppress("UNCHECKED_CAST")
        val handler = when (listener) {
            is UserStreamListener -> UserStreamHandler(listener)
            is SampleStreamListener -> SampleStreamHandler(listener)
            is FilterStreamListener -> FilterStreamHandler(listener)
            is LivePipelineListener -> LivePipelineHandler(listener)
            else -> throw UnsupportedOperationException("Unsupported StreamListener: ${listener.javaClass.canonicalName}")
        } as H

        return StreamProcessor(this, handler)
    }
}

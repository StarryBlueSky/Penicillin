package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.core.streaming.*

data class StreamResponse<L: StreamListener, H: StreamHandler<L>>(
    override val request: HttpRequest,
    override val response: HttpResponse,
    override val action: ApiAction<StreamResponse<L, H>>
): ApiResponse {

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

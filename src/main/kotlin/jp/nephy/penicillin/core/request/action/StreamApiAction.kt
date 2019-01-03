package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.StreamResponse
import jp.nephy.penicillin.core.streaming.StreamHandler
import jp.nephy.penicillin.core.streaming.StreamListener
import kotlinx.coroutines.CancellationException

data class StreamApiAction<L: StreamListener, H: StreamHandler<L>>(override val request: ApiRequest): ApiAction<StreamResponse<L, H>> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): StreamResponse<L, H> {
        val (request, response) = execute(request)
        checkError(request, response)

        return StreamResponse(request, response, this)
    }
}

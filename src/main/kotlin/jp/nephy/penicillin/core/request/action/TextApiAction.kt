package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.TextResponse
import kotlinx.coroutines.CancellationException

data class TextApiAction(override val request: ApiRequest): ApiAction<TextResponse> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): TextResponse {
        val (request, response) = execute(request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        return TextResponse(request, response, content.orEmpty(), this)
    }
}

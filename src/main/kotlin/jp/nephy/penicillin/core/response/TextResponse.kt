package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.request.action.ApiAction

data class TextResponse(
    override val request: HttpRequest,
    override val response: HttpResponse,
    override val content: String,
    override val action: ApiAction<TextResponse>
): ApiResponse, CompletedResponse

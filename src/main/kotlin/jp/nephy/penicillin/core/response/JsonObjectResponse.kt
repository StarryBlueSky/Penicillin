package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

data class JsonObjectResponse<M: PenicillinModel>(
    override val model: KClass<M>,
    val result: M,
    override val request: HttpRequest,
    override val response: HttpResponse,
    override val content: String,
    override val action: ApiAction<JsonObjectResponse<M>>
): ApiResponse, JsonResponse<M, JsonObject>, CompletedResponse {

    override val json: JsonObject
        get() = result.json
}

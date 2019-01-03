package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.jsonkt.JsonArray
import jp.nephy.jsonkt.toJsonArray
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

data class JsonArrayResponse<M: PenicillinModel>(
    override val model: KClass<M>,
    override val request: HttpRequest,
    override val response: HttpResponse,
    override val content: String,
    override val action: ApiAction<JsonArrayResponse<M>>
// TODO: immutable list
): ApiResponse, JsonResponse<M, JsonArray>, CompletedResponse, ArrayList<M>() {

    override val json: JsonArray
        get() = map { it.json }.toJsonArray()
}

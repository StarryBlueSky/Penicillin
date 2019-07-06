package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.endpoints.PremiumSearchEnvironment
import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

/**
 * Created on 2019/07/07.
 */
data class EnvironmentalJsonObjectResponse<M: PenicillinModel>(
    override val client: ApiClient,
    override val model: KClass<M>,

    /**
     * Result of response.
     */
    val result: M,

    override val request: HttpRequest,
    override val response: HttpResponse,
    override val content: String,
    override val action: ApiAction<EnvironmentalJsonObjectResponse<M>>,
    val environment: PremiumSearchEnvironment
): ApiResponse<EnvironmentalJsonObjectResponse<M>>, JsonResponse<M, JsonObject>, CompletedResponse {

    override val json: JsonObject
        get() = result.json

    override fun close() {
        response.close()
    }
}
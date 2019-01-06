package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

data class JsonObjectApiAction<M: PenicillinModel>(override val request: ApiRequest, override val model: KClass<M>): JsonRequest<M>, ApiAction<JsonObjectResponse<M>> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): JsonObjectResponse<M> {
        val (request, response) = execute(request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonObjectSafe(model == Empty::class) ?: throw PenicillinLocalizedException(
            LocalizedString.JsonParsingFailed, request, response, null, content
        )
        val result = json.parseSafe(model, content) ?: throw PenicillinLocalizedException(
            LocalizedString.JsonModelCastFailed, request, response, null, model.simpleName, content
        )

        return JsonObjectResponse(model, result, request, response, content.orEmpty(), this)
    }
}

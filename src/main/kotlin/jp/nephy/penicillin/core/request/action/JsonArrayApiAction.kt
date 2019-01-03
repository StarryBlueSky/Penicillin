package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonArrayResponse
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

data class JsonArrayApiAction<M: PenicillinModel>(override val request: ApiRequest, override val model: KClass<M>): JsonRequest<M>, ApiAction<JsonArrayResponse<M>> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): JsonArrayResponse<M> {
        val (request, response) = execute(request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonArraySafe() ?: throw PenicillinLocalizedException(
            LocalizedString.JsonParsingFailed, request, response, null, content
        )

        return JsonArrayResponse(model, request, response, content, this).apply {
            addAll(json.parseSafe(model, content))
        }
    }
}

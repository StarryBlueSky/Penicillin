package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.CursorJsonObjectResponse
import jp.nephy.penicillin.extensions.complete
import jp.nephy.penicillin.extensions.cursor.byCursor
import jp.nephy.penicillin.models.PenicillinCursorModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

data class CursorJsonObjectApiAction<M: PenicillinCursorModel>(override val request: ApiRequest, override val model: KClass<M>): JsonRequest<M>, ApiAction<CursorJsonObjectResponse<M>> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): CursorJsonObjectResponse<M> {
        val (request, response) = execute(request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonObjectSafe() ?: throw PenicillinLocalizedException(
            LocalizedString.JsonParsingFailed,
            request,
            response,
            null,
            content
        )
        val result = json.parseSafe(model, content) ?: throw PenicillinLocalizedException(
            LocalizedString.JsonModelCastFailed, request, response, null, model.simpleName, content
        )

        return CursorJsonObjectResponse(model, result, request, response, content, this)
    }

    // TODO: make it suspend, extension
    fun untilLast() = sequence {
        val first = complete()
        yield(first)
        var cursor = first.result.nextCursor
        while (cursor != 0L) {
            val result = try {
                first.byCursor(cursor).complete()
            } catch (e: PenicillinException) {
                break
            }

            yield(result)
            cursor = result.result.nextCursor
        }
    }
}

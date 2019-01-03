package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException

typealias JoinedJsonObjectActionCallback<M> = (results: List<List<JsonObjectResponse<*>>>) -> JsonObjectApiAction<M>

// TODO
data class JoinedJsonObjectActions<M: PenicillinModel, T: PenicillinModel>(
    private val actions: List<PenicillinMultipleJsonObjectActions<M>>,
    private val finalizer: JoinedJsonObjectActionCallback<T>
): ApiAction<JsonObjectResponse<T>> {
    override val request: ApiRequest
        get() = actions.last().first.request

    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): JsonObjectResponse<T> {
        return finalizer(actions.map { it.await() }).await()
    }
}

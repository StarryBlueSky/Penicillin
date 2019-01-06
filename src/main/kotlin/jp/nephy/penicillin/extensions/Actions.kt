package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.request.action.JoinedJsonObjectActionCallback
import jp.nephy.penicillin.core.request.action.JoinedJsonObjectActions
import jp.nephy.penicillin.core.request.action.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel

inline fun <reified M: PenicillinModel> List<JsonObjectResponse<*>>.filter(): List<JsonObjectResponse<M>> {
    @Suppress("UNCHECKED_CAST") return filter { it.model == M::class }.map { it as JsonObjectResponse<M> }
}

fun <M: PenicillinModel, T: PenicillinModel> List<PenicillinMultipleJsonObjectActions<M>>.join(finalizer: JoinedJsonObjectActionCallback<T>): JoinedJsonObjectActions<M, T> {
    return JoinedJsonObjectActions(this, finalizer)
}

package jp.nephy.penicillin.core.request

import jp.nephy.penicillin.core.request.action.*
import jp.nephy.penicillin.core.session.Session
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinCursorModel
import jp.nephy.penicillin.models.PenicillinModel

class ApiRequest(val session: Session, val builder: ApiRequestBuilder) {
    inline fun <reified M: PenicillinModel> jsonObject(): JsonObjectApiAction<M> {
        return JsonObjectApiAction(this, M::class)
    }

    fun empty(): JsonObjectApiAction<Empty> {
        return JsonObjectApiAction(this, Empty::class)
    }

    inline fun <reified M: PenicillinModel> jsonArray(): JsonArrayApiAction<M> {
        return JsonArrayApiAction(this, M::class)
    }

    inline fun <reified M: PenicillinCursorModel> cursorJsonObject(): CursorJsonObjectApiAction<M> {
        return CursorJsonObjectApiAction(this, M::class)
    }

    fun text(): TextApiAction {
        return TextApiAction(this)
    }

    fun <L: StreamListener, H: StreamHandler<L>> stream(): StreamApiAction<L, H> {
        return StreamApiAction(this)
    }
}

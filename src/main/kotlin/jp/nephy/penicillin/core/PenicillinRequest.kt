package jp.nephy.penicillin.core

import jp.nephy.penicillin.core.streaming.StreamHandler
import jp.nephy.penicillin.core.streaming.StreamListener
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.PenicillinCursorModel
import jp.nephy.penicillin.models.PenicillinModel

class PenicillinRequest(val session: Session, val builder: PenicillinRequestBuilder) {
    inline fun <reified M: PenicillinModel> jsonObject(): PenicillinJsonObjectAction<M> {
        return PenicillinJsonObjectAction(this, M::class)
    }

    fun empty(): PenicillinJsonObjectAction<Empty> {
        return PenicillinJsonObjectAction(this, Empty::class)
    }

    inline fun <reified M: PenicillinModel> jsonArray(): PenicillinJsonArrayAction<M> {
        return PenicillinJsonArrayAction(this, M::class)
    }

    inline fun <reified M: PenicillinCursorModel> cursorJsonObject(): PenicillinCursorJsonObjectAction<M> {
        return PenicillinCursorJsonObjectAction(this, M::class)
    }

    fun text(): PenicillinTextAction {
        return PenicillinTextAction(this)
    }

    fun <L: StreamListener, H: StreamHandler<L>> stream(): PenicillinStreamAction<L, H> {
        return PenicillinStreamAction(this)
    }
}

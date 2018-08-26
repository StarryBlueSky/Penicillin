package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.DirectMessageEvent
import jp.nephy.penicillin.models.Empty

class DirectMessageEvent(override val client: PenicillinClient): Endpoint {
    fun create(vararg options: Pair<String, String>)= client.session.postObject<Empty>("/direct_messages/events/new.json") {
        json(*options)
    }

    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<DirectMessageEvent>("/direct_messages/events/show.json") {
        query("id" to id, *options)
    }

    fun list(count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getList<DirectMessageEvent>("/direct_messages/events/list.json") {
        query("count" to count, *options)
    }
}

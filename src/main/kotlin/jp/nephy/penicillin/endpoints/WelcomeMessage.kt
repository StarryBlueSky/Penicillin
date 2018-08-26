package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.WelcomeMessage

class WelcomeMessage(override val client: PenicillinClient): Endpoint {
    fun create(vararg options: Pair<String, String>)= client.session.postObject<Empty>("/direct_messages/welcome_messages/new.json") {
        json(*options)
    }

    fun delete(id: Long, vararg options: Pair<String, String>)= client.session.deleteObject<Empty>("/direct_messages/welcome_messages/destroy.json") {
        query("id" to id, *options)
    }

    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<WelcomeMessage>("/direct_messages/welcome_messages/show.json") {
        query("id" to id, *options)
    }

    fun list(count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getList<WelcomeMessage>("/direct_messages/welcome_messages/list.json") {
        query("count" to count, *options)
    }
}

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Empty
import jp.nephy.penicillin.models.WelcomeMessageRule

class WelcomeMessageRule(override val client: PenicillinClient): Endpoint {
    fun delete(id: Long, vararg options: Pair<String, String>)= client.session.deleteObject<Empty>("/direct_messages/welcome_messages/rules/destroy.json") {
        query("id" to id, *options)
    }

    fun create(vararg options: Pair<String, String>)= client.session.postObject<Empty>("/direct_messages/welcome_messages/rules/new.json") {
        json(*options)
    }

    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<WelcomeMessageRule>("/direct_messages/welcome_messages/rules/show.json") {
        query("id" to id, *options)
    }

    fun list(count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getList<WelcomeMessageRule>("/direct_messages/welcome_messages/rules/list.json") {
        query("count" to count, *options)
    }
}

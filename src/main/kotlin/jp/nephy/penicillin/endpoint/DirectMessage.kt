package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.DirectMessage


class DirectMessage(override val client: PenicillinClient): Endpoint {
    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<DirectMessage>("/direct_messages/show.json") {
        query("id" to id, *options)
    }

    fun list(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<DirectMessage>("/direct_messages.json") {
        query("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun sentMessages(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, page: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<DirectMessage>("/direct_messages/sent.json") {
        query("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "page" to page, *options)
    }

    fun create(text: String, userId: Long? = null, screenName: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<DirectMessage>("/direct_messages/new.json") {
        form("text" to text, "user_id" to userId, "screen_name" to screenName, *options)
    }

    fun delete(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<DirectMessage>("/direct_messages/destroy.json") {
        form("id" to id, "include_entities" to includeEntities, *options)
    }
}

package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.Status


class Favorite(override val client: PenicillinClient): Endpoint {
    fun list(userId: Long? = null, screenName: String? = null, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/favorites/list.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
    }

    fun create(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/favorites/create.json") {
        form("id" to id, "include_entities" to includeEntities, *options)
    }

    fun destroy(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/favorites/destroy.json") {
        form("id" to id, "include_entities" to includeEntities, *options)
    }
}

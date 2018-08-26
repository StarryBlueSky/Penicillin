package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers
import jp.nephy.penicillin.models.User


class Block(override val client: PenicillinClient): Endpoint {
    fun listIds(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/blocks/ids.json") {
        query("stringify_ids" to stringifyIds, *options)
    }

    fun list(includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorUsers>("/blocks/list.json") {
        query("include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun create(screenName: String? = null, userId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/blocks/create.json") {
        form("screen_name" to screenName, "user_id" to userId, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun destroy(screenName: String? = null, userId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/blocks/destroy.json") {
        form("screen_name" to screenName, "user_id" to userId, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }
}

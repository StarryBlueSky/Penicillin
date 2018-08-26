package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers
import jp.nephy.penicillin.models.User


class Mute(override val client: PenicillinClient): Endpoint {
    fun listIds(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/mutes/users/ids.json") {
        query("stringify_ids" to stringifyIds, *options)
    }

    fun list(includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorUsers>("/mutes/users/list.json") {
        query("include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun create(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/mutes/users/create.json") {
        form("screen_name" to screenName, "user_id" to userId, *options)
    }

    fun destroy(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/mutes/users/destroy.json") {
        form("screen_name" to screenName, "user_id" to userId, *options)
    }
}

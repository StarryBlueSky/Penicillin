package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers


class Friend(override val client: PenicillinClient): Endpoint {
    fun listIds(userId: Long? = null, screenName: String? = null, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/friends/ids.json") {
        query("user_id" to userId, "screen_name" to screenName, "stringify_ids" to stringifyIds, "count" to count, *options)
    }

    fun list(userId: Long? = null, screenName: String? = null, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorUsers>("/friends/list.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
    }

    @PrivateEndpoint
    fun followingList(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.getCursorObject<CursorUsers>("/friends/following/list.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }
}

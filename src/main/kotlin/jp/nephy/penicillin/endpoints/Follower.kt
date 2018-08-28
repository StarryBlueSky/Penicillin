package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers


class Follower(override val client: PenicillinClient): Endpoint {
    fun listIds(userId: Long? = null, screenName: String? = null, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/followers/ids.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "stringify_ids" to stringifyIds, "count" to count, *options)
    }.cursorJsonObject<CursorIds>()

    fun list(userId: Long? = null, screenName: String? = null, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/followers/list.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
    }.cursorJsonObject<CursorUsers>()
}

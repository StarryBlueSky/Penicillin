@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers

class Friend(override val client: PenicillinClient): Endpoint {
    fun listIds(stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friends/ids.json") {
        parameter("stringify_ids" to stringifyIds, "count" to count, *options)
    }.cursorJsonObject<CursorIds>()

    fun listIds(userId: Long, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friends/ids.json") {
        parameter("user_id" to userId, "stringify_ids" to stringifyIds, "count" to count, *options)
    }.cursorJsonObject<CursorIds>()

    fun listIds(screenName: String, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friends/ids.json") {
        parameter("screen_name" to screenName, "stringify_ids" to stringifyIds, "count" to count, *options)
    }.cursorJsonObject<CursorIds>()

    fun list(count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/friends/list.json") {
            parameter("count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
        }.cursorJsonObject<CursorUsers>()

    fun list(userId: Long, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/friends/list.json") {
            parameter("user_id" to userId, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
        }.cursorJsonObject<CursorUsers>()

    fun list(screenName: String, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/friends/list.json") {
                parameter("screen_name" to screenName, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
            }.cursorJsonObject<CursorUsers>()

    // TODO
    @PrivateEndpoint
    fun followingList(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friends/following/list.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }.cursorJsonObject<CursorUsers>()
}

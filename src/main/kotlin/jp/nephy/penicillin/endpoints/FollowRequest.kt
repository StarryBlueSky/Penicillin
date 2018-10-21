@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.User


class FollowRequest(override val client: PenicillinClient): Endpoint {
    fun received(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/incoming.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    fun sent(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/outgoing.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    @PrivateEndpoint
    fun accept(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/accept.json") {
        body {
            form {
                add("ext" to "mediaColor", "include_entities" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "screen_name" to screenName, "user_id" to userId, *options)
            }
        }
    }.jsonObject<User>()
}

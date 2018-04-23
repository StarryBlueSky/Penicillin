package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.User


class FollowRequest(override val client: PenicillinClient): Endpoint {
    fun received(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/friendships/incoming.json") {
        query("stringify_ids" to stringifyIds, *options)
    }

    fun sent(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/friendships/outgoing.json") {
        query("stringify_ids" to stringifyIds, *options)
    }

    @PrivateEndpoint
    fun accept(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/friendships/accept.json") {
        form("ext" to "mediaColor", "include_entities" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "screen_name" to screenName, "user_id" to userId, *options)
    }
}

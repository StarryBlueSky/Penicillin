package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.User


class Friendship(override val client: PenicillinClient): Endpoint {
    fun show(sourceId: Long? = null, sourceScreenName: String? = null, targetId: Long? = null, targetScreenName: String? = null, vararg options: Pair<String, Any?>)= client.session.getObject<FriendshipsShow>("/friendships/show.json") {
        query("source_id" to sourceId, "source_screen_name" to sourceScreenName, "target_id" to targetId, "target_screen_name" to targetScreenName, *options)
    }

    fun lookup(screenNames: List<String>? = null, userIds: List<Long>? = null, vararg options: Pair<String, Any?>)= client.session.getList<FriendshipsLookup>("/friendships/lookup.json") {
        query("screen_name" to screenNames?.joinToString(","), "user_id" to userIds?.joinToString(","), *options)
    }

    fun noRetweetsIds(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<FriendshipsNoRetweetsIds>("/friendships/no_retweets/ids.json") {
        query("stringify_ids" to stringifyIds, *options)
    }

    fun create(screenName: String? = null, userId: Long? = null, follow: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/friendships/create.json") {
        form("ext" to "mediaColor", "handles_challenges" to "1", "include_entities" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", onlyOfficialClient = true)
        form("screen_name" to screenName, "user_id" to userId, "follow" to follow, *options)
    }

    fun destroy(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/friendships/destroy.json") {
        form("screen_name" to screenName, "user_id" to userId, *options)
    }

    fun update(screenName: String? = null, userId: Long? = null, devide: Boolean? = null, retweets: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Relationship>("/friendships/update.json") {
        form("screen_name" to screenName, "user_id" to userId, "device" to devide, "retweets" to retweets, *options)
    }

    @PrivateEndpoint
    fun readAll(vararg options: Pair<String, Any?>)= client.session.postList<User>("/friendships/read_all.json") {
        form("cards_platform" to "iPhone-13", "contributor_details" to "1", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "tweet_mode" to "extended", *options)
    }
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.User


class Friendship(override val client: PenicillinClient): Endpoint {
    fun show(sourceId: Long? = null, sourceScreenName: String? = null, targetId: Long? = null, targetScreenName: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/show.json") {
        parameter("source_id" to sourceId, "source_screen_name" to sourceScreenName, "target_id" to targetId, "target_screen_name" to targetScreenName, *options)
    }.jsonObject<FriendshipsShow>()

    fun lookup(screenNames: List<String>? = null, userIds: List<Long>? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/lookup.json") {
        parameter("screen_name" to screenNames?.joinToString(","), "user_id" to userIds?.joinToString(","), *options)
    }.jsonArray<FriendshipsLookup>()

    fun noRetweetsIds(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/no_retweets/ids.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.jsonObject<FriendshipsNoRetweetsIds>()

    fun create(screenName: String? = null, userId: Long? = null, follow: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/create.json") {
        body {
            form {
                add("ext" to "mediaColor", "handles_challenges" to "1", "include_entities" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", emulationMode = EmulationMode.TwitterForiPhone)
                add("screen_name" to screenName, "user_id" to userId, "follow" to follow, *options)
            }
        }
    }.jsonObject<User>()

    fun destroy(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/destroy.json") {
        body {
            form {
                add("screen_name" to screenName, "user_id" to userId, *options)
            }
        }
    }.jsonObject<User>()

    fun update(screenName: String? = null, userId: Long? = null, devide: Boolean? = null, retweets: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/update.json") {
        body {
            form {
                add("screen_name" to screenName, "user_id" to userId, "device" to devide, "retweets" to retweets, *options)
            }
        }
    }.jsonObject<Relationship>()

    @PrivateEndpoint
    fun readAll(vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/read_all.json") {
        body {
            form {
                add("cards_platform" to "iPhone-13", "contributor_details" to "1", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "tweet_mode" to "extended", *options)
            }
        }
    }.jsonArray<User>()
}

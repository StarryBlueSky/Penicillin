@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.models.ActivityEvent

class Activity(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint(EmulationMode.TwitterForiPhone)
    fun aboutMe(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/activity/about_me.json") {
        parameter(
            "cards_platform" to "iPhone-13",
            "contributor_details" to "1",
            "count" to (count ?: "21"),
            "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,self_thread,stickerInfo",
            "filters" to "",
            "include_alert" to "0",
            "include_cards" to "1",
            "include_carousels" to "1",
            "include_entities" to "1",
            "include_ext_media_color" to "true",
            "include_media_features" to "true",
            "include_my_retweet" to "1",
            "include_profile_interstitial_type" to "true",
            "include_profile_location" to "true",
            "include_reply_count" to "1",
            "include_user_entities" to "true",
            "include_user_hashtag_entities" to "true",
            "include_user_mention_entities" to "true",
            "include_user_symbol_entities" to "true",
            "model_version" to "8",
            "tweet_mode" to "extended",
            *options,
            emulationMode = EmulationMode.TwitterForiPhone
        )
    }.jsonArray<ActivityEvent>()

    @PrivateEndpoint(EmulationMode.Tweetdeck)
    fun byFriends(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/activity/by_friends.json") {
        parameter(
            "count" to (count ?: 40),
            "skip_aggregation" to true,
            "cards_platform" to "Web-13",
            "include_entities" to 1,
            "include_user_entities" to 1,
            "include_cards" to 1,
            "send_error_codes" to 1,
            "tweet_mode" to "extended",
            "include_ext_alt_text" to true,
            "include_reply_count" to true,
            *options,
            emulationMode = EmulationMode.Tweetdeck
        )
    }.jsonArray<ActivityEvent>()
}

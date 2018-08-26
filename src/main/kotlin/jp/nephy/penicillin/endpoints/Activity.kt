package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.ActivityAboutMe
import jp.nephy.penicillin.models.ActivityAboutMeUnread

class Activity(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun activityAboutMe(sinceId: Long? = null, vararg options: Pair<String, Any?>)= client.session.getObject<ActivityAboutMe>("/activity/about_me.json") {
        query("cards_platform" to "iPhone-13", "contributor_details" to "1", "count" to "50", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "filters" to "", "include_alert" to "0", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "latest_results" to "true", "model_version" to "9", "since_id" to sinceId, "tweet_mode" to "extended", *options)
    }

    @PrivateEndpoint
    fun activityAboutMeUnread(vararg options: Pair<String, Any?>)= client.session.getObject<ActivityAboutMeUnread>("/activity/about_me/unread.json") {
        query("cursor" to false, *options)
    }
}

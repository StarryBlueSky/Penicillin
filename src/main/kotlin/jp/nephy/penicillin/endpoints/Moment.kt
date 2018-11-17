@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.MomentGuide

class Moment(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun guide(vararg options: Pair<String, Any?>) = client.session.get("/1.1/moments/guide.json") {
        parameter(
            "cards_platform" to "iPhone-13",
            "contributor_details" to "1",
            "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
            "hydration_count" to "2",
            "include_blocked_by" to "1",
            "include_blocking" to "1",
            "include_capsule_contents" to "0",
            "include_cards" to "1",
            "include_carousels" to "1",
            "include_entities" to "1",
            "include_ext_media_color" to "true",
            "include_media_features" to "true",
            "include_my_retweet" to "1",
            "include_profile_interstitial_type" to "true",
            "include_profile_location" to "true",
            "include_reply_count" to "1",
            "include_trends" to "1",
            "include_user_entities" to "true",
            "include_user_hashtag_entities" to "true",
            "include_user_mention_entities" to "true",
            "include_user_symbol_entities" to "true",
            "live_video_in_hero" to "1",
            "pc" to "1",
            "tweet_mode" to "extended",
            "v" to "1473704494",
            *options
        )
    }.jsonObject<MomentGuide>()
}

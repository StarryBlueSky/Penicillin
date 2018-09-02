package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.models.Empty

class Notifications(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun all() = client.session.get("/2/notifications/all.json") {
        parameter(
                "cards_platform" to "iPhone-13",
                "contributor_details" to "1",
                "cursor" to null,
                "ext" to "altText,highlightedLabel,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
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
                "last_seen_cursor" to null,
                "request_context" to "polling",
                "tweet_mode" to "extended",
                emulationMode = EmulationMode.TwitterForiPhone
        )
    }.jsonObject<Empty>()
}

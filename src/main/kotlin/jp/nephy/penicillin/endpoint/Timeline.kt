package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.Status


class Timeline(override val client: PenicillinClient): Endpoint {
    fun home(count: Int? = null, sinceId: Long? = null, maxId: Long? = null, trimUser: Boolean? = null, excludeReplies: Boolean? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/statuses/home_timeline.json") {
        query("count" to count, "since_id" to sinceId, "max_id" to maxId, "trim_user" to trimUser, "exclude_replies" to excludeReplies, "include_entities" to includeEntities, *options)
    }

    fun mention(count: Int? = null, sinceId: Long? = null, maxId: Long? = null, trimUser: Boolean? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getList<Status>("/statuses/mentions_timeline.json") {
        query("cards_platform" to "iPhone-13", "contributor_details" to "1", "count" to "20", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "filters" to "", "forceBuckets" to "", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "tweet_mode" to "extended", onlyOfficialClient = true)
        query("count" to count, "since_id" to sinceId, "max_id" to maxId, "trim_user" to trimUser, "include_entities" to includeEntities, *options)
    }

    fun user(userId: Long? = null, screenName: String? = null, count: Int? = null, sinceId: Long? = null, maxId: Long? = null, trimUser: Boolean? = null, excludeReplies: Boolean? = null, includeRTs: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/statuses/user_timeline.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, "since_id" to sinceId, "max_id" to maxId, "trim_user" to trimUser, "exclude_replies" to excludeReplies, "include_rts" to includeRTs, *options)
    }
}

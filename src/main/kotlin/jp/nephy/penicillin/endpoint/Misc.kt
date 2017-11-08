package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

@Suppress("UNUSED")
class Misc(private val client: Client) {
    @GET @UndocumentedAPI
    fun getContributees(vararg options: Pair<String, String?>): ResponseList<Contributees> {
        return client.session.new()
                .url("/users/contributees.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @UndocumentedAPI
    fun getPendingContributees(vararg options: Pair<String, String?>): ResponseList<Contributees> {
        return client.session.new()
                .url("/users/contributees/pending.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @UndocumentedAPI
    fun activateGuest(vararg options: Pair<String, String?>): ResponseObject<Guest> {
        return client.session.new()
                .url("/guest/activate.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST @UndocumentedAPI
    fun setPushDestination(token: String, uuid: String, appVersion: Int=28, deviceModel: String="iPhone", deviceName: String="iPhone 6s+", environment: Int=3, lang: String="ja", systemName: String="iOS", systemVersion: String="10.2", vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/push_destinations.json")
                .dataAsForm("token" to token)
                .dataAsForm("uuid" to uuid)
                .dataAsForm("app_version" to appVersion)
                .dataAsForm("device_model" to deviceModel)
                .dataAsForm("device_name" to deviceName)
                .dataAsForm("environment" to environment)
                .dataAsForm("lang" to lang)
                .dataAsForm("system_name" to systemName)
                .dataAsForm("system_version" to systemVersion)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getExtendedProfile(screenName: String, includeBirthdate: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<ExtendedProfile> {
        return client.session.new()
                .url("/users/extended_profile.json")
                .param("screen_name" to screenName)
                .param("include_birthdate" to includeBirthdate)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST @UndocumentedAPI
    fun setNotificationApplicationState(token: String, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/notifications/application_state/set_current_user.json")
                .dataAsForm("token" to token)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getBadgeCount(vararg options: Pair<String, String?>): ResponseObject<BadgeCount> {
        return client.session.new()
                .url("https://api.twitter.com/2/badge_count/badge_count.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to 1)
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("include_cards" to 1)
                .param("include_carousels" to 1)
                .param("include_entities" to 1)
                .param("include_ext_media_color" to true)
                .param("include_media_features" to true)
                .param("include_my_retweet" to 1)
                .param("include_profile_interstitial_type" to true)
                .param("include_profile_location" to true)
                .param("include_reply_count" to 1)
                .param("include_user_entities" to true)
                .param("include_user_hashtag_entities" to true)
                .param("include_user_mention_entities" to true)
                .param("include_user_symbol_entities" to true)
                .param("model_version" to 9)
                .param("tweet_mode" to "extended")
                .param("use_truncated_counts" to false)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getActivityAboutMeUnread(vararg options: Pair<String, String?>): ResponseObject<ActivityAboutMeUnread> {
        return client.session.new()
                .url("https://api.twitter.com/1.1/activity/about_me/unread.json")
                .param("cursor" to false)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getPushDevice(uuid: String, vararg options: Pair<String, String?>): ResponseObject<PushDevice> {
        return client.session.new()
                .url("/push_destinations/device.json")
                .param("environment" to 3)
                .param("uuid" to uuid)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST @UndocumentedAPI
    fun logPromotedContent(impressionId: String, epochMs: Long, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/promoted_content/log.json")
                .dataAsForm("epoch_ms" to epochMs)
                .dataAsForm("event" to "long_dwell_view")
                .dataAsForm("impression_id" to impressionId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getDMUserUpdate(cursor: String, vararg options: Pair<String, String?>): ResponseObject<DMUserUpdate> {
        return client.session.new()
                .url("/dm/user_updates.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to "1")
                .param("cursor" to cursor)
                .param("dm_users" to "true")
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("include_cards" to "1")
                .param("include_carousels" to "1")
                .param("include_entities" to "1")
                .param("include_ext_media_color" to "true")
                .param("include_groups" to "true")
                .param("include_inbox_timelines" to "true")
                .param("include_media_features" to "true")
                .param("include_my_retweet" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_reply_count" to "1")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("tweet_mode" to "extended")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getPromptSuggest(vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/prompts/suggest.json")
                .param("client_namespace" to "native")
                .param("consecutive_days" to "1")
                .param("force_fatigue_on_override" to "false")
                .param("format" to "home_timeline")
                .param("has_unknown_phone_number" to "false")
                .param("lang" to "ja")
                .param("notifications_device" to "true")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getActivityAboutMe(sinceId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<ActivityAboutMe> {
        return client.session.new()
                .url("/activity/about_me.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to "1")
                .param("count" to "50")
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("filters" to "")
                .param("include_alert" to "0")
                .param("include_cards" to "1")
                .param("include_carousels" to "1")
                .param("include_entities" to "1")
                .param("include_ext_media_color" to "true")
                .param("include_media_features" to "true")
                .param("include_my_retweet" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_reply_count" to "1")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("latest_results" to "true")
                .param("model_version" to "9")
                .param("since_id" to sinceId)
                .param("tweet_mode" to "extended")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getTypeahead(q: String?=null, vararg options: Pair<String, String?>): ResponseObject<SearchTypeahead> {
        return client.session.new()
                .url("/search/typeahead.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to "1")
                .param("count" to "1200")
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("include_cards" to "1")
                .param("include_carousels" to "1")
                .param("include_entities" to "1")
                .param("include_ext_media_color" to "true")
                .param("include_media_features" to "true")
                .param("include_my_retweet" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_reply_count" to "1")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("media_tagging_in_prefetch" to "true")
                .param("prefetch" to "true")
                .param("result_type" to "all")
                .param("src" to "search_box")
                .param("tweet_mode" to "extended")
                .param("users_cache_age" to "146522")
                .param("q" to q)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getMomentGuide(vararg options: Pair<String, String?>): ResponseObject<MomentGuide> {
        return client.session.new()
                .url("/moments/guide.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to "1")
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("hydration_count" to "2")
                .param("include_blocked_by" to "1")
                .param("include_blocking" to "1")
                .param("include_capsule_contents" to "0")
                .param("include_cards" to "1")
                .param("include_carousels" to "1")
                .param("include_entities" to "1")
                .param("include_ext_media_color" to "true")
                .param("include_media_features" to "true")
                .param("include_my_retweet" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_reply_count" to "1")
                .param("include_trends" to "1")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("live_video_in_hero" to "1")
                .param("pc" to "1")
                .param("tweet_mode" to "extended")
                .param("v" to "1473704494")
                .params(*options)
                .get()
                .getResponseObject()
    }
}

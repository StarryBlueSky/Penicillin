package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.api.ResponseObject
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class AccountVerifyCredentialsModel(val json: JsonElement) {
    val advertiserAccountServiceLevels by json.byNullableArray("advertiser_account_service_levels") // ["analytics"]
    val advertiserAccountType by json.byNullableString("advertiser_account_type") // "promotable_user"
    val analyticsType by json.byNullableString("analytics_type") // "enabled"
    val businessProfileState by json.byNullableString("business_profile_state") // "none"
    val canMediaTag by json.byNullableBool("can_media_tag") // true
    val contributorsEnabled by json.byNullableBool("contributors_enabled") // false
    val createdAt by json.byNullableString("created_at") // "Sun Feb 21 05:49:47 +0000 2016"
    val defaultProfile by json.byNullableBool("default_profile") // false
    val defaultProfileImage by json.byNullableBool("default_profile_image") // false
    val description by json.byNullableString // "Pythonを書きます ワンライナーで書くのが好きです\n\nhttps://t.co/8udUawi147\n@GeForce_GTX1080"
    val email by json.byNullableString // ""
    val entities by json.byNullableObject // {"url": {"urls": [{"url": "https://t.co/UDBxOXmvoG", "expanded_url": "https://slash.nephy.jp", "display_url": "slash.nephy.jp", "indices": [0, 23]}]}, "description": {"urls": [{"url": "https://t.co/8udUawi147", "expanded_url": "https://steamcommunity.com/id/slashnephy", "display_url": "steamcommunity.com/id/slashnephy", "indices": [29, 52]}]}}
    val fastFollowersCount by json.byNullableInt("fast_followers_count") // 0
    val favouritesCount by json.byNullableInt("favourites_count") // 775
    val followRequestSent by json.byNullableBool("follow_request_sent") // false
    val followedBy by json.byNullableBool("followed_by") // false
    val followersCount by json.byNullableInt("followers_count") // 212
    val following by json.byNullableBool // false
    val friendsCount by json.byNullableInt("friends_count") // 304
    val geoEnabled by json.byNullableBool("geo_enabled") // true
    val hasCustomTimelines by json.byNullableBool("has_custom_timelines") // true
    val hasExtendedProfile by json.byNullableBool("has_extended_profile") // true
    val id by json.byNullableLong // 701282649466245120
    val idStr by json.byNullableString("id_str") // "701282649466245120"
    val isTranslationEnabled by json.byNullableBool("is_translation_enabled") // false
    val isTranslator by json.byNullableBool("is_translator") // false
    val lang by json.byNullableString // "ja"
    val listedCount by json.byNullableInt("listed_count") // 13
    val location by json.byNullableString // "Sendai, Japan"
    val mediaCount by json.byNullableInt("media_count") // 385
    val name by json.byNullableString // "(*'~')?"
    val needsPhoneVerification by json.byNullableBool("needs_phone_verification") // false
    val normalFollowersCount by json.byNullableInt("normal_followers_count") // 212
    val notifications by json.byNullableBool // false
    val phone by json.byNullableObject // {"id": 797659999379943424, "created_at": "Sun Nov 13 04:38:58 +0000 2016", "address": "+81**********", "address_for_sms": "40404", "verified": true, "enabled_for": "#", "carrier": "jp.softbank_mobile", "country_name": "日本", "country_code": "81", "device_type": "phone"}
    val pinnedTweetIds by json.byNullableArray("pinned_tweet_ids") // [901277812425859072]
    val pinnedTweetIdsStr by json.byNullableArray("pinned_tweet_ids_str") // ["901277812425859072"]
    val profileBackgroundColor by json.byNullableString("profile_background_color") // "000000"
    val profileBackgroundImageUrl by json.byNullableString("profile_background_image_url") // "http://abs.twimg.com/images/themes/theme1/bg.png"
    val profileBackgroundImageUrlHttps by json.byNullableString("profile_background_image_url_https") // "https://abs.twimg.com/images/themes/theme1/bg.png"
    val profileBackgroundTile by json.byNullableBool("profile_background_tile") // false
    val profileBannerUrl by json.byNullableString("profile_banner_url") // "https://pbs.twimg.com/profile_banners/701282649466245120/1497800398"
    val profileImageUrl by json.byNullableString("profile_image_url") // "http://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg"
    val profileImageUrlHttps by json.byNullableString("profile_image_url_https") // "https://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg"
    val profileLinkColor by json.byNullableString("profile_link_color") // "1B95E0"
    val profileSidebarBorderColor by json.byNullableString("profile_sidebar_border_color") // "000000"
    val profileSidebarFillColor by json.byNullableString("profile_sidebar_fill_color") // "000000"
    val profileTextColor by json.byNullableString("profile_text_color") // "000000"
    val profileUseBackgroundImage by json.byNullableBool("profile_use_background_image") // false
    val protected by json.byNullableBool // false
    val screenName by json.byNullableString("screen_name") // "SlashNephy"
    val status by json.byNullableObject // {"created_at": "Sat Sep 02 06:40:27 +0000 2017", "id": 903870217176006656, "id_str": "903870217176006656", "text": "(´；ω；｀)ﾌﾞﾜｯ", "truncated": false, "entities": {"hashtags": [], "symbols": [], "user_mentions": [], "urls": []}, "source": "<a href=\"https://about.twitter.com/products/tweetdeck\" rel=\"nofollow\">TweetDeck</a>", "in_reply_to_status_id": null, "in_reply_to_status_id_str": null, "in_reply_to_user_id": null, "in_reply_to_user_id_str": null, "in_reply_to_screen_name": null, "geo": null, "coordinates": null, "place": null, "contributors": null, "is_quote_status": false, "retweet_count": 0, "favorite_count": 0, "favorited": false, "retweeted": false, "lang": "ja", "supplemental_language": null}
    val statusesCount by json.byNullableInt("statuses_count") // 4588
    val suspended by json.byNullableBool // false
    val timeZone by json.byNullableString("time_zone") // "Tokyo"
    val translatorType by json.byNullableString("translator_type") // "none"
    val url by json.byNullableString // "https://t.co/UDBxOXmvoG"
    val utcOffset by json.byNullableInt("utc_offset") // 32400
    val verified by json.byNullableBool // false
}

class AccountVerifyCredentials(oauth: OAuthRequestHandler): AbsOAuthGet<ResponseObject<AccountVerifyCredentialsModel>>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/account/verify_credentials.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter().apply {
        put("include_entities", null)
        put("skip_status", null)
        put("include_email", null)
    }
}

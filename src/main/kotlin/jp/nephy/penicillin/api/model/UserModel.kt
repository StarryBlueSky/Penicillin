package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byNullableURL

class UserModel(val json: JsonElement) {
    val businessProfileState by json.byNullableString("business_profile_state") // "none"
    val canMediaTag by json.byNullableBool("can_media_tag") // true
    val contributorsEnabled by json.byNullableBool("contributors_enabled") // false
    val createdAt by json.byNullableString("created_at") // "Thu Nov 24 04:55:50 +0000 2011"
    val defaultProfile by json.byNullableBool("default_profile") // true
    val defaultProfileImage by json.byNullableBool("default_profile_image") // false
    val description by json.byNullableString // "声優をしています。"
    val entities by json.byNullableObject // {"url": {"urls": [{"url": "https://t.co/eq4TGE7ov1", "expanded_url": "http://ayanataketatsu.jp/", "display_url": "ayanataketatsu.jp", "indices": [0, 23]}]}, "description": {"urls": []}}
    val fastFollowersCount by json.byNullableInt("fast_followers_count") // 0
    val favouritesCount by json.byNullableInt("favourites_count") // 334
    val followRequestSent by json.byNullableBool("follow_request_sent") // false
    val followedBy by json.byNullableBool("followed_by") // false
    val followersCount by json.byNullableInt("followers_count") // 844119
    val following by json.byNullableBool // false
    val friendsCount by json.byNullableInt("friends_count") // 315
    val geoEnabled by json.byNullableBool("geo_enabled") // false
    val hasCustomTimelines by json.byNullableBool("has_custom_timelines") // false
    val hasExtendedProfile by json.byNullableBool("has_extended_profile") // true
    val id by json.byNullableInt // 420077970
    val idStr by json.byNullableString("id_str") // "420077970"
    val isTranslationEnabled by json.byNullableBool("is_translation_enabled") // true
    val isTranslator by json.byNullableBool("is_translator") // false
    val lang by json.byNullableString // "ja"
    val listedCount by json.byNullableInt("listed_count") // 14413
    val location by json.byNullableString // "おなかいっぱい☺"
    val mediaCount by json.byNullableInt("media_count") // 358
    val name by json.byNullableString // "竹達 彩奈"
    val normalFollowersCount by json.byNullableInt("normal_followers_count") // 844119
    val notifications by json.byNullableBool // false
    val pinnedTweetIds by json.byNullableArray("pinned_tweet_ids") // []
    val pinnedTweetIdsStr by json.byNullableArray("pinned_tweet_ids_str") // []
    val profileBackgroundColor by json.byNullableString("profile_background_color") // "C0DEED"
    val profileBackgroundImageUrl by json.byNullableURL("profile_background_image_url") // "http://abs.twimg.com/images/themes/theme1/bg.png"
    val profileBackgroundImageUrlHttps by json.byNullableURL("profile_background_image_url_https") // "https://abs.twimg.com/images/themes/theme1/bg.png"
    val profileBackgroundTile by json.byNullableBool("profile_background_tile") // false
    val profileImageUrl by json.byNullableURL("profile_image_url") // "http://pbs.twimg.com/profile_images/883709821509279744/FELB3yOF_normal.jpg"
    val profileImageUrlHttps by json.byNullableURL("profile_image_url_https") // "https://pbs.twimg.com/profile_images/883709821509279744/FELB3yOF_normal.jpg"
    val profileLinkColor by json.byNullableString("profile_link_color") // "1DA1F2"
    val profileSidebarBorderColor by json.byNullableString("profile_sidebar_border_color") // "C0DEED"
    val profileSidebarFillColor by json.byNullableString("profile_sidebar_fill_color") // "DDEEF6"
    val profileTextColor by json.byNullableString("profile_text_color") // "333333"
    val profileUseBackgroundImage by json.byNullableBool("profile_use_background_image") // true
    val protected by json.byNullableBool // false
    val screenName by json.byNullableString("screen_name") // "Ayana_take"
    val statusesCount by json.byNullableInt("statuses_count") // 8794
    val timeZone by json.byNullableString("time_zone") // "Hawaii"
    val translatorType by json.byNullableString("translator_type") // "none"
    val url by json.byNullableString // "https://t.co/eq4TGE7ov1"
    val utcOffset by json.byNullableInt("utc_offset") // -36000
    val verified by json.byNullableBool // true
}

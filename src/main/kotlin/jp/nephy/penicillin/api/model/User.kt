package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.*

open class User(val json: JsonElement) {
    val businessProfileState by json.byString("business_profile_state")
    val canMediaTag by json.byBool("can_media_tag")
    val contributorsEnabled by json.byBool("contributors_enabled")
    val createdAt by json.byString("created_at")
    val defaultProfile by json.byBool("default_profile")
    val defaultProfileImage by json.byBool("default_profile_image")
    val description by json.byString
    val entities by json.byUserEntity
    val fastFollowersCount by json.byInt("fast_followers_count")
    val favouritesCount by json.byInt("favourites_count")
    val followRequestSent by json.byBool("follow_request_sent")
    val followedBy by json.byBool("followed_by")
    val followersCount by json.byInt("followers_count")
    val following by json.byBool
    val friendsCount by json.byInt("friends_count")
    val geoEnabled by json.byBool("geo_enabled")
    val hasCustomTimelines by json.byBool("has_custom_timelines")
    val hasExtendedProfile by json.byBool("has_extended_profile")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val isTranslationEnabled by json.byBool("is_translation_enabled")
    val isTranslator by json.byBool("is_translator")
    val lang by json.byString
    val listedCount by json.byInt("listed_count")
    val location by json.byString
    val mediaCount by json.byInt("media_count")
    val name by json.byString
    val normalFollowersCount by json.byInt("normal_followers_count")
    val notifications by json.byBool
    val pinnedTweetIds by json.byStatusIDArray("pinned_tweet_ids")
    val pinnedTweetIdsStr by json.byStringArray("pinned_tweet_ids_str")
    val profileBackgroundColor by json.byString("profile_background_color")
    val profileBackgroundImageUrl by json.byNullableURL("profile_background_image_url")
    val profileBackgroundImageUrlHttps by json.byNullableURL("profile_background_image_url_https")
    val profileBackgroundTile by json.byBool("profile_background_tile")
    val profileImageUrl by json.byURL("profile_image_url")
    val profileImageUrlHttps by json.byURL("profile_image_url_https")
    val profileLinkColor by json.byString("profile_link_color")
    val profileSidebarBorderColor by json.byString("profile_sidebar_border_color")
    val profileSidebarFillColor by json.byString("profile_sidebar_fill_color")
    val profileTextColor by json.byString("profile_text_color")
    val profileUseBackgroundImage by json.byBool("profile_use_background_image")
    val protected by json.byBool
    val screenName by json.byString("screen_name")
    val statusesCount by json.byInt("statuses_count")
    val timeZone by json.byString("time_zone")
    val translatorType by json.byString("translator_type")
    val url by json.byNullableURL
    val utcOffset by json.byInt("utc_offset")
    val verified by json.byBool
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.model.special.CreatedAt
import jp.nephy.penicillin.model.special.Language


open class User(final override val json: JsonObject): JsonModel {
    val advertiserAccountServiceLevels by json.byStringList("advertiser_account_service_levels")
    val advertiserAccountType by json.byNullableString("advertiser_account_type")
    val analyticsType by json.byNullableString("analytics_type")
    val businessProfileState by json.byNullableString("business_profile_state")
    val canMediaTag by json.byNullableBool("can_media_tag")
    val contributorsEnabled by json.byBool("contributors_enabled")
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val defaultProfile by json.byBool("default_profile")
    val defaultProfileImage by json.byBool("default_profile_image")
    val description by json.byNullableString
    val entities by json.byModel<UserEntity?>()
    val fastFollowersCount by json.byNullableInt("fast_followers_count")
    val favouritesCount by json.byInt("favourites_count")
    val followRequestSent by json.byNullableBool("follow_request_sent")
    val followedBy by json.byNullableBool("followed_by")
    val followersCount by json.byInt("followers_count")
    val following by json.byNullableBool
    val friendsCount by json.byInt("friends_count")
    val geoEnabled by json.byBool("geo_enabled")
    val hasCustomTimelines by json.byNullableBool("has_custom_timelines")
    val hasExtendedProfile by json.byNullableBool("has_extended_profile")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val isTranslationEnabled by json.byNullableBool("is_translation_enabled")
    val isTranslator by json.byBool("is_translator")
    val lang by json.byLambda { Language(string) }
    val listedCount by json.byInt("listed_count")
    val location by json.byNullableString
    val mediaCount by json.byNullableInt("media_count")
    val name by json.byString
    val needsPhoneVerification by json.byNullableBool("needs_phone_verification")
    val normalFollowersCount by json.byNullableInt("normal_followers_count")
    val notifications by json.byNullableBool
    val pinnedTweetIds by json.byLongList("pinned_tweet_ids")
    val pinnedTweetIdsStr by json.byStringList("pinned_tweet_ids_str")
    val profileBackgroundColor by json.byString("profile_background_color")
    val profileBackgroundImageUrl by json.byString("profile_background_image_url")
    val profileBackgroundImageUrlHttps by json.byString("profile_background_image_url_https")
    val profileBackgroundTile by json.byBool("profile_background_tile")
    val profileBannerExtension by json.byModel<ProfileImageExtension?>(key = "profile_banner_extensions")
    val profileBannerUrl by json.byString("profile_banner_url")
    val profileImageExtensions by json.byModel<ProfileImageExtension?>(key = "profile_image_extensions")
    val profileImageUrl by json.byString("profile_image_url")
    val profileImageUrlHttps by json.byString("profile_image_url_https")
    val profileInterstitialType by json.byNullableString("profile_interstitial_type")
    val profileLinkColor by json.byString("profile_link_color")
    val profileSidebarBorderColor by json.byString("profile_sidebar_border_color")
    val profileSidebarFillColor by json.byString("profile_sidebar_fill_color")
    val profileTextColor by json.byString("profile_text_color")
    val profileUseBackgroundImage by json.byBool("profile_use_background_image")
    val protected by json.byBool
    val screenName by json.byString("screen_name")
    val status by json.byModel<Status?>()
    val statusesCount by json.byInt("statuses_count")
    val suspended by json.byNullableBool
    val timeZone by json.byNullableString("time_zone")
    val translatorType by json.byString("translator_type")
    val url by json.byNullableString
    val utcOffset by json.byNullableInt("utc_offset")
    val verified by json.byBool
    val withheldInCountries by json.byStringList("withheld_in_countries")
    val withheldScope by json.byNullableString("withheld_scope")
}

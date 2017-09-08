package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.byNullableBool
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.api.ResponseObject
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class AccountSettingsModel(val json: JsonElement) {
    val addressBookLiveSyncEnabled by json.byNullableBool("address_book_live_sync_enabled") // false
    val allowAdsPersonalization by json.byNullableBool("allow_ads_personalization") // false
    val allowAuthenticatedPeriscopeRequests by json.byNullableBool("allow_authenticated_periscope_requests") // true
    val allowContributorRequest by json.byNullableString("allow_contributor_request") // "following"
    val allowDmGroupsFrom by json.byNullableString("allow_dm_groups_from") // "following"
    val allowDmsFrom by json.byNullableString("allow_dms_from") // "all"
    val allowLocationHistoryPersonalization by json.byNullableBool("allow_location_history_personalization") // true
    val allowLoggedOutDevicePersonalization by json.byNullableBool("allow_logged_out_device_personalization") // true
    val allowMediaTagging by json.byNullableString("allow_media_tagging") // "following"
    val allowSharingDataForThirdPartyPersonalization by json.byNullableBool("allow_sharing_data_for_third_party_personalization") // true
    val alwaysUseHttps by json.byNullableBool("always_use_https") // true
    val countryCode by json.byNullableString("country_code") // null
    val discoverableByEmail by json.byNullableBool("discoverable_by_email") // false
    val discoverableByMobilePhone by json.byNullableBool("discoverable_by_mobile_phone") // false
    val displaySensitiveMedia by json.byNullableBool("display_sensitive_media") // true
    val dmReceiptSetting by json.byNullableString("dm_receipt_setting") // "all_enabled"
    val geoEnabled by json.byNullableBool("geo_enabled") // true
    val language by json.byNullableString // "ja"
    val notificationsAbuseFilterQuality by json.byNullableString("notifications_abuse_filter_quality") // "unfiltered"
    val notificationsFilterQuality by json.byNullableString("notifications_filter_quality") // "unfiltered"
    val personalizedTrends by json.byNullableBool("personalized_trends") // true
    val protected by json.byNullableBool // false
    val screenName by json.byNullableString("screen_name") // "SlashNephy"
    val settingsMetadata by json.byNullableObject("settings_metadata") // {}
    val sleepTime by json.byNullableObject("sleep_time") // {"enabled": false, "end_time": null, "start_time": null}
    val timeZone by json.byNullableObject("time_zone") // {"name": "Tokyo", "utc_offset": 32400, "tzinfo_name": "Asia/Tokyo"}
    val translatorType by json.byNullableString("translator_type") // "none"
    val universalQualityFilteringEnabled by json.byNullableString("universal_quality_filtering_enabled") // "enabled"
    val useCookiePersonalization by json.byNullableBool("use_cookie_personalization") // true
}

class AccountSettings(oauth: OAuthRequestHandler): AbsOAuthGet<ResponseObject<AccountSettingsModel>>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/account/settings.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val requestsPer15mins = 15
    override val defaultParameter = Parameter()
}

package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.bySettingMetadata
import jp.nephy.penicillin.api.bySleepTime
import jp.nephy.penicillin.api.byTimeZone

class Setting(val json: JsonElement) {
    val addressBookLiveSyncEnabled by json.byBool("address_book_live_sync_enabled")
    val allowAdsPersonalization by json.byBool("allow_ads_personalization")
    val allowAuthenticatedPeriscopeRequests by json.byBool("allow_authenticated_periscope_requests")
    val allowContributorRequest by json.byString("allow_contributor_request")
    val allowDmGroupsFrom by json.byString("allow_dm_groups_from")
    val allowDmsFrom by json.byString("allow_dms_from")
    val allowLocationHistoryPersonalization by json.byBool("allow_location_history_personalization")
    val allowLoggedOutDevicePersonalization by json.byBool("allow_logged_out_device_personalization")
    val allowMediaTagging by json.byString("allow_media_tagging")
    val allowSharingDataForThirdPartyPersonalization by json.byBool("allow_sharing_data_for_third_party_personalization")
    val alwaysUseHttps by json.byBool("always_use_https")
    val countryCode by json.byString("country_code")
    val discoverableByEmail by json.byBool("discoverable_by_email")
    val discoverableByMobilePhone by json.byBool("discoverable_by_mobile_phone")
    val displaySensitiveMedia by json.byBool("display_sensitive_media")
    val dmReceiptSetting by json.byString("dm_receipt_setting")
    val geoEnabled by json.byBool("geo_enabled")
    val language by json.byString
    val notificationsAbuseFilterQuality by json.byString("notifications_abuse_filter_quality")
    val notificationsFilterQuality by json.byString("notifications_filter_quality")
    val personalizedTrends by json.byBool("personalized_trends")
    val protected by json.byBool
    val screenName by json.byString("screen_name")
    val settingsMetadata by json.bySettingMetadata("settings_metadata")
    val sleepTime by json.bySleepTime("sleep_time")
    val timeZone by json.byTimeZone("time_zone")
    val translatorType by json.byString("translator_type")
    val universalQualityFilteringEnabled by json.byString("universal_quality_filtering_enabled")
    val useCookiePersonalization by json.byBool("use_cookie_personalization")
}

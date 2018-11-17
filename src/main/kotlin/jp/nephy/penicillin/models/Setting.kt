@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.Language

data class Setting(override val json: JsonObject): PenicillinModel {
    val addressBookLiveSyncEnabled by boolean("address_book_live_sync_enabled")
    val allowAdsPersonalization by boolean("allow_ads_personalization")
    val allowAuthenticatedPeriscopeRequests by boolean("allow_authenticated_periscope_requests")
    val allowContributorRequest by string("allow_contributor_request")
    val allowDmGroupsFrom by string("allow_dm_groups_from")
    val allowDmsFrom by string("allow_dms_from")
    val allowLocationHistoryPersonalization by boolean("allow_location_history_personalization")
    val allowLoggedOutDevicePersonalization by boolean("allow_logged_out_device_personalization")
    val allowMediaTagging by string("allow_media_tagging")
    val allowSharingDataForThirdPartyPersonalization by boolean("allow_sharing_data_for_third_party_personalization")
    val altTextComposeEnabled by nullableString("alt_text_compose_enabled")
    val alwaysUseHttps by boolean("always_use_https")
    val countryCode by string("country_code")
    val discoverableByEmail by boolean("discoverable_by_email")
    val discoverableByMobilePhone by boolean("discoverable_by_mobile_phone")
    val displaySensitiveMedia by boolean("display_sensitive_media")
    val dmReceiptSetting by string("dm_receipt_setting")
    val geoEnabled by boolean("geo_enabled")
    val language by lambda { Language(it.string) }
    val mentionFilter by nullableString("mention_filter")
    val notificationsAbuseFilterQuality by string("notifications_abuse_filter_quality")
    val notificationsFilterQuality by string("notifications_filter_quality")
    val personalizedTrends by boolean("personalized_trends")
    val protected by boolean
    val rankedTimelineEligible by nullableString("ranked_timeline_eligible")
    val rankedTimelineSetting by nullableInt("ranked_timeline_setting")
    val screenName by string("screen_name")
    val settingsMetadata by model<SettingMetadata>(key = "settings_metadata")
    val sleepTime by model<SleepTime>(key = "sleep_time")
    val timeZone by model<TimeZone>(key = "time_zone")
    val translatorType by string("translator_type")
    val universalQualityFilteringEnabled by string("universal_quality_filtering_enabled")
    val useCookiePersonalization by boolean("use_cookie_personalization")
}

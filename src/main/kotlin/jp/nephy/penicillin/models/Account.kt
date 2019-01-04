@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

object Account {
    data class Settings(override val json: JsonObject): PenicillinModel {
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
        // val language by string
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

        data class SettingMetadata(override val json: JsonObject): PenicillinModel

        data class SleepTime(override val json: JsonObject): PenicillinModel {
            val enabled by boolean
            val startTime by nullableLong("start_time")
            val endTime by nullableLong("end_time")
        }

        data class TimeZone(override val json: JsonObject): PenicillinModel {
            val name by string
            val utcOffset by int("utc_offset")
            val tzinfoName by string("tzinfo_name")
        }
    }

    data class VerifyCredentials(val parentJson: JsonObject): CommonUser(parentJson) {
        val email by string
        val phone by model<Phone>()

        data class Phone(override val json: JsonObject): PenicillinModel {
            val address by nullableString
            val addressForSms by nullableString("address_for_sms")
            val carrier by nullableString
            val countryCode by nullableString("country_code")
            val countryName by nullableString("country_name")
            val createdAt by nullableString("created_at")
            val deviceType by nullableString("device_type")
            val enabledFor by nullableString("enabled_for")
            val id by nullableLong
            val verified by nullableBoolean
        }
    }
}

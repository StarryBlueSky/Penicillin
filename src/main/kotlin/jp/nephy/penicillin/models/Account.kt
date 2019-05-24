/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.penicillinModel

object Account {
    data class Settings(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
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
        val languageRaw by string("language")
        val mentionFilter by nullableString("mention_filter")
        val notificationsAbuseFilterQuality by string("notifications_abuse_filter_quality")
        val notificationsFilterQuality by string("notifications_filter_quality")
        val personalizedTrends by boolean("personalized_trends")
        val protected by boolean
        val rankedTimelineEligible by nullableString("ranked_timeline_eligible")
        val rankedTimelineSetting by nullableInt("ranked_timeline_setting")
        val screenName by string("screen_name")
        val settingsMetadata by penicillinModel<SettingMetadata>("settings_metadata")
        val sleepTime by penicillinModel<SleepTime>("sleep_time")
        val timeZone by penicillinModel<TimeZone>("time_zone")
        val translatorType by string("translator_type")
        val universalQualityFilteringEnabled by string("universal_quality_filtering_enabled")
        val useCookiePersonalization by boolean("use_cookie_personalization")

        data class SettingMetadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel

        data class SleepTime(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val enabled by boolean
            val startTime by nullableLong("start_time")
            val endTime by nullableLong("end_time")
        }

        data class TimeZone(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val name by string
            val utcOffset by int("utc_offset")
            val tzinfoName by string("tzinfo_name")
        }
    }

    data class VerifyCredentials(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonUser(parentJson, parentClient) {
        val email by string
        val phone by penicillinModel<Phone>()

        data class Phone(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
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

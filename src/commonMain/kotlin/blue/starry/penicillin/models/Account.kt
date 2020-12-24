/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient

public object Account {
    public data class Settings(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val addressBookLiveSyncEnabled: Boolean by boolean("address_book_live_sync_enabled")
        public val allowAdsPersonalization: Boolean by boolean("allow_ads_personalization")
        public val allowAuthenticatedPeriscopeRequests: Boolean by boolean("allow_authenticated_periscope_requests")
        public val allowContributorRequest: String by string("allow_contributor_request")
        public val allowDmGroupsFrom: String by string("allow_dm_groups_from")
        public val allowDmsFrom: String by string("allow_dms_from")
        public val allowLocationHistoryPersonalization: Boolean by boolean("allow_location_history_personalization")
        public val allowLoggedOutDevicePersonalization: Boolean by boolean("allow_logged_out_device_personalization")
        public val allowMediaTagging: String by string("allow_media_tagging")
        public val allowSharingDataForThirdPartyPersonalization: Boolean by boolean("allow_sharing_data_for_third_party_personalization")
        public val altTextComposeEnabled: String? by nullableString("alt_text_compose_enabled")
        public val alwaysUseHttps: Boolean by boolean("always_use_https")
        public val countryCode: String by string("country_code")
        public val discoverableByEmail: Boolean by boolean("discoverable_by_email")
        public val discoverableByMobilePhone: Boolean by boolean("discoverable_by_mobile_phone")
        public val displaySensitiveMedia: Boolean by boolean("display_sensitive_media")
        public val dmReceiptSetting: String by string("dm_receipt_setting")
        public val geoEnabled: Boolean by boolean("geo_enabled")
        public val languageRaw: String by string("language")
        public val mentionFilter: String? by nullableString("mention_filter")
        public val notificationsAbuseFilterQuality: String by string("notifications_abuse_filter_quality")
        public val notificationsFilterQuality: String by string("notifications_filter_quality")
        public val personalizedTrends: Boolean by boolean("personalized_trends")
        public val protected: Boolean by boolean
        public val rankedTimelineEligible: String? by nullableString("ranked_timeline_eligible")
        public val rankedTimelineSetting: Int? by nullableInt("ranked_timeline_setting")
        public val screenName: String by string("screen_name")
        public val settingsMetadata: SettingMetadata by model("settings_metadata") { SettingMetadata(it, client) }
        public val sleepTime: SleepTime by model("sleep_time") { SleepTime(it, client) }
        public val timeZone: TimeZone by model("time_zone") { TimeZone(it, client) }
        public val translatorType: String by string("translator_type")
        public val universalQualityFilteringEnabled: String by string("universal_quality_filtering_enabled")
        public val useCookiePersonalization: Boolean by boolean("use_cookie_personalization")

        public data class SettingMetadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel

        public data class SleepTime(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val enabled: Boolean by boolean
            public val startTime: Long? by nullableLong("start_time")
            public val endTime: Long? by nullableLong("end_time")
        }

        public data class TimeZone(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val name: String by string
            public val utcOffset: Int by int("utc_offset")
            public val tzinfoName: String by string("tzinfo_name")
        }
    }

    public data class VerifyCredentials(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonUser(parentJson, parentClient) {
        public val email: String by string
        public val phone: Phone by model { Phone(it, client) }

        public data class Phone(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val address: String? by nullableString
            public val addressForSms: String? by nullableString("address_for_sms")
            public val carrier: String? by nullableString
            public val countryCode: String? by nullableString("country_code")
            public val countryName: String? by nullableString("country_name")
            public val createdAt: String? by nullableString("created_at")
            public val deviceType: String? by nullableString("device_type")
            public val enabledFor: String? by nullableString("enabled_for")
            public val id: Long? by nullableLong
            public val verified: Boolean? by nullableBoolean
        }
    }
}

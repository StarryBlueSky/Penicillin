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

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.nullablePenicillinModel
import jp.nephy.penicillin.extensions.penicillinModel
import jp.nephy.penicillin.models.entities.UserEntity

data class User(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonUser(parentJson, parentClient)

abstract class CommonUser(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    val advertiserAccountServiceLevels by stringList("advertiser_account_service_levels")
    val advertiserAccountType by nullableString("advertiser_account_type")
    val analyticsType by nullableString("analytics_type")
    val businessProfileState by nullableString("business_profile_state")
    val canMediaTag by nullableBoolean("can_media_tag")
    val contributorsEnabled by boolean("contributors_enabled")
    // val createdAt by string("created_at")
    val defaultProfile by boolean("default_profile")
    val defaultProfileImage by boolean("default_profile_image")
    val description by nullableString
    val entities by nullablePenicillinModel<UserEntity>()
    val fastFollowersCount by nullableInt("fast_followers_count")
    val favouritesCount by int("favourites_count")
    val followRequestSent by nullableBoolean("follow_request_sent")
    val followedBy by nullableBoolean("followed_by")
    val followersCount by int("followers_count")
    val following by nullableBoolean
    val friendsCount by int("friends_count")
    val geoEnabled by boolean("geo_enabled")
    val hasCustomTimelines by nullableBoolean("has_custom_timelines")
    val hasExtendedProfile by nullableBoolean("has_extended_profile")
    val id by long
    val idStr by string("id_str")
    val isTranslationEnabled by nullableBoolean("is_translation_enabled")
    val isTranslator by boolean("is_translator")
    // val lang by string
    val listedCount by int("listed_count")
    val location by nullableString
    val mediaCount by nullableInt("media_count")
    val name by string
    val needsPhoneVerification by nullableBoolean("needs_phone_verification")
    val normalFollowersCount by nullableInt("normal_followers_count")
    val notifications by nullableBoolean
    val pinnedTweetIds by longList("pinned_tweet_ids")
    val pinnedTweetIdsStr by stringList("pinned_tweet_ids_str")
    val profileBackgroundColor by string("profile_background_color")
    val profileBackgroundImageUrl by string("profile_background_image_url")
    val profileBackgroundImageUrlHttps by string("profile_background_image_url_https")
    val profileBackgroundTile by boolean("profile_background_tile")
    val profileBannerExtension by nullablePenicillinModel<ProfileImageExtension>("profile_banner_extensions")
    val profileBannerUrl by nullableString("profile_banner_url")
    val profileImageExtensions by nullablePenicillinModel<ProfileImageExtension>("profile_image_extensions")
    val profileImageUrl by string("profile_image_url")
    val profileImageUrlHttps by string("profile_image_url_https")
    val profileInterstitialType by nullableString("profile_interstitial_type")
    val profileLinkColor by string("profile_link_color")
    val profileSidebarBorderColor by string("profile_sidebar_border_color")
    val profileSidebarFillColor by string("profile_sidebar_fill_color")
    val profileTextColor by string("profile_text_color")
    val profileUseBackgroundImage by boolean("profile_use_background_image")
    val protected by boolean
    val screenName by string("screen_name")
    val status by nullablePenicillinModel<Status>()
    val statusesCount by int("statuses_count")
    val suspended by nullableBoolean
    val timeZone by nullableString("time_zone")
    val translatorType by string("translator_type")
    val url by nullableString
    val utcOffset by nullableInt("utc_offset")
    val verified by boolean
    val withheldInCountries by stringList("withheld_in_countries")
    val withheldScope by nullableString("withheld_scope")

    data class ProfileImageExtension(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val mediaColor by penicillinModel<MediaColor>()

        data class MediaColor(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val r by jsonObject
            val ttl by int
        }
    }
}

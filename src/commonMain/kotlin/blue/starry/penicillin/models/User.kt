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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient

import blue.starry.penicillin.models.entities.UserEntity

data class User(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonUser(parentJson, parentClient)

abstract class CommonUser(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    val id by long
    val idStr by string("id_str")
    
    val name by string
    val screenName by string("screen_name")
    val location by string
    val profileLocation by nullableJsonElement
    val description by string
    val url by nullableString
    
    val entities by nullableModel { UserEntity(it, client) }
    val protected by boolean
    val followersCount by int("followers_count")
    val friendsCount by int("friends_count")
    val listedCount by int("listed_count")
    val createdAtRaw by string("created_at")
    val favouritesCount by int("favourites_count")
    val utcOffset by int("utc_offset")
    val timeZone by string("time_zone")
    val geoEnabled by boolean("geo_enabled")
    val verified by boolean
    val statusesCount by int("statuses_count")
    val langRaw by nullableString("lang")
    val contributorsEnabled by boolean("contributors_enabled")
    val isTranslator by boolean("is_translator")
    val isTranslationEnabled by boolean("is_translation_enabled")
    
    // Profile background
    val profileBackgroundColor by string("profile_background_color")
    val profileBackgroundImageUrl by nullableString("profile_background_image_url")
    val profileBackgroundImageUrlHttps by nullableString("profile_background_image_url_https")
    val profileBackgroundTile by boolean("profile_background_tile")
    
    // Profile image
    val profileImageUrl by string("profile_image_url")
    val profileImageUrlHttps by string("profile_image_url_https")
    
    val profileLinkColor by string("profile_link_color")
    val profileSidebarBorderColor by string("profile_sidebar_border_color")
    val profileSidebarFillColor by string("profile_sidebar_fill_color")
    val profileTextColor by string("profile_text_color")
    val profileUseBackgroundImage by boolean("profile_use_background_image")
    
    val hasExtendedProfile by boolean("has_extended_profile")
    val defaultProfile by boolean("default_profile")
    val defaultProfileImage by boolean("default_profile_image")
    val following by boolean
    val followRequestSent by boolean("follow_request_sent")
    val notifications by boolean
    
    // Unknown
    val muting by boolean
    val blocking by boolean
    val blockedBy by boolean("blocked_by")
    val liveFollowing by boolean("live_following")
    val advertiserAccountServiceLevels by stringList("advertiser_account_service_levels")
    val advertiserAccountType by nullableString("advertiser_account_type")
    val analyticsType by nullableString("analytics_type")
    val businessProfileState by nullableString("business_profile_state")
    val canMediaTag by nullableBoolean("can_media_tag")
    val fastFollowersCount by nullableInt("fast_followers_count")
    val followedBy by nullableBoolean("followed_by")
    val hasCustomTimelines by nullableBoolean("has_custom_timelines")
    val mediaCount by nullableInt("media_count")
    val needsPhoneVerification by nullableBoolean("needs_phone_verification")
    val normalFollowersCount by nullableInt("normal_followers_count")
    val pinnedTweetIds by longList("pinned_tweet_ids")
    val pinnedTweetIdsStr by stringList("pinned_tweet_ids_str")
    val profileBannerExtension by nullableModel("profile_banner_extensions") { ProfileImageExtension(it, client) }
    val profileBannerUrl by nullableString("profile_banner_url")
    val profileImageExtensions by nullableModel("profile_image_extensions") { ProfileImageExtension(it, client) }
    val profileInterstitialType by nullableString("profile_interstitial_type")
    val status by nullableModel { Status(it, client) }
    val suspended by nullableBoolean
    val translatorType by string("translator_type")
    val withheldInCountries by stringList("withheld_in_countries")
    val withheldScope by nullableString("withheld_scope")

    data class ProfileImageExtension(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val mediaColor by model { MediaColor(it, client) }

        data class MediaColor(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val r by jsonObject
            val ttl by int
        }
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as? User)?.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

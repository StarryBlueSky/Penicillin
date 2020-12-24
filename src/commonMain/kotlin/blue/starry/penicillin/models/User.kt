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

import blue.starry.jsonkt.JsonElement
import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient

import blue.starry.penicillin.models.entities.UserEntity

public data class User(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonUser(parentJson, parentClient)

public abstract class CommonUser(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    public val id: Long by long
    public val idStr: String by string("id_str")
    
    public val name: String by string
    public val screenName: String by string("screen_name")
    public val location: String by string
    public val profileLocation: JsonElement? /* = kotlinx.serialization.json.JsonElement? */ by nullableJsonElement
    public val description: String by string
    public val url: String? by nullableString
    
    public val entities: UserEntity? by nullableModel { UserEntity(it, client) }
    public val protected: Boolean by boolean
    public val followersCount: Int by int("followers_count")
    public val friendsCount: Int by int("friends_count")
    public val listedCount: Int by int("listed_count")
    public val createdAtRaw: String by string("created_at")
    public val favouritesCount: Int by int("favourites_count")
    public val utcOffset: Int by int("utc_offset")
    public val timeZone: String by string("time_zone")
    public val geoEnabled: Boolean by boolean("geo_enabled")
    public val verified: Boolean by boolean
    public val statusesCount: Int by int("statuses_count")
    public val langRaw: String? by nullableString("lang")
    public val contributorsEnabled: Boolean by boolean("contributors_enabled")
    public val isTranslator: Boolean by boolean("is_translator")
    public val isTranslationEnabled: Boolean by boolean("is_translation_enabled")
    
    // Profile background
    public val profileBackgroundColor: String by string("profile_background_color")
    public val profileBackgroundImageUrl: String? by nullableString("profile_background_image_url")
    public val profileBackgroundImageUrlHttps: String? by nullableString("profile_background_image_url_https")
    public val profileBackgroundTile: Boolean by boolean("profile_background_tile")
    
    // Profile image
    public val profileImageUrl: String by string("profile_image_url")
    public val profileImageUrlHttps: String by string("profile_image_url_https")
    
    public val profileLinkColor: String by string("profile_link_color")
    public val profileSidebarBorderColor: String by string("profile_sidebar_border_color")
    public val profileSidebarFillColor: String by string("profile_sidebar_fill_color")
    public val profileTextColor: String by string("profile_text_color")
    public val profileUseBackgroundImage: Boolean by boolean("profile_use_background_image")
    
    public val hasExtendedProfile: Boolean by boolean("has_extended_profile")
    public val defaultProfile: Boolean by boolean("default_profile")
    public val defaultProfileImage: Boolean by boolean("default_profile_image")
    public val following: Boolean by boolean
    public val followRequestSent: Boolean by boolean("follow_request_sent")
    public val notifications: Boolean by boolean
    
    // Unknown
    public val muting: Boolean by boolean
    public val blocking: Boolean by boolean
    public val blockedBy: Boolean by boolean("blocked_by")
    public val liveFollowing: Boolean by boolean("live_following")
    public val advertiserAccountServiceLevels: List<String> by stringList("advertiser_account_service_levels")
    public val advertiserAccountType: String? by nullableString("advertiser_account_type")
    public val analyticsType: String? by nullableString("analytics_type")
    public val businessProfileState: String? by nullableString("business_profile_state")
    public val canMediaTag: Boolean? by nullableBoolean("can_media_tag")
    public val fastFollowersCount: Int? by nullableInt("fast_followers_count")
    public val followedBy: Boolean? by nullableBoolean("followed_by")
    public val hasCustomTimelines: Boolean? by nullableBoolean("has_custom_timelines")
    public val mediaCount: Int? by nullableInt("media_count")
    public val needsPhoneVerification: Boolean? by nullableBoolean("needs_phone_verification")
    public val normalFollowersCount: Int? by nullableInt("normal_followers_count")
    public val pinnedTweetIds: List<Long> by longList("pinned_tweet_ids")
    public val pinnedTweetIdsStr: List<String> by stringList("pinned_tweet_ids_str")
    public val profileBannerExtension: ProfileImageExtension? by nullableModel("profile_banner_extensions") { ProfileImageExtension(it, client) }
    public val profileBannerUrl: String? by nullableString("profile_banner_url")
    public val profileImageExtensions: ProfileImageExtension? by nullableModel("profile_image_extensions") { ProfileImageExtension(it, client) }
    public val profileInterstitialType: String? by nullableString("profile_interstitial_type")
    public val status: Status? by nullableModel { Status(it, client) }
    public val suspended: Boolean? by nullableBoolean
    public val translatorType: String by string("translator_type")
    public val withheldInCountries: List<String> by stringList("withheld_in_countries")
    public val withheldScope: String? by nullableString("withheld_scope")

    public data class ProfileImageExtension(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val mediaColor: MediaColor by model { MediaColor(it, client) }

        public data class MediaColor(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val r: kotlinx.serialization.json.JsonObject by jsonObject
            public val ttl: Int by int
        }
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as? User)?.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

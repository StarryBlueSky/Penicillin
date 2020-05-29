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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.models.builder

import blue.starry.jsonkt.jsonArrayOf
import blue.starry.jsonkt.jsonObjectOf
import blue.starry.jsonkt.parseObject
import blue.starry.jsonkt.toJsonObject
import jp.nephy.penicillin.core.session.NoopApiClient
import jp.nephy.penicillin.models.User
import java.time.temporal.TemporalAccessor
import kotlin.collections.set

/**
 * Custom payload builder for [User].
 */
class CustomUserBuilder: JsonBuilder<User>, JsonMap by jsonMapOf(
    "id" to userId,
    "id_str" to userId.toString(),
    "name" to "Tweetstorm",
    "screen_name" to "Tweetstorm",
    "location" to null,
    "description" to "This account is dummy and is used to deliver internal messages.",
    "url" to "https://github.com/SlashNephy/Tweetstorm",
    "entities" to jsonObjectOf(
        "url" to jsonObjectOf(
            "urls" to jsonArrayOf(
                jsonObjectOf(
                    "display_url" to "github.com/SlashNephy/Tweetstorm",
                    "url" to "https://t.co/Cn0EQY6Yzd",
                    "indices" to jsonArrayOf(0, 23),
                    "expanded_url" to "https://github.com/SlashNephy/Tweetstorm"
                )
            )
        ),
        "description" to jsonObjectOf(
            "urls" to jsonArrayOf()
        )
    ),
    "protected" to false,
    "followers_count" to 0,
    "friends_count" to 0,
    "listed_count" to 0,
    "created_at" to null,
    "favourites_count" to 0,
    "utc_offset" to null,
    "time_zone" to null,
    "geo_enabled" to false,
    "verified" to false,
    "statuses_count" to 0,
    "lang" to "ja",
    "is_translator" to false,
    "is_translation_enabled" to false,
    "profile_background_color" to "000000",
    "profile_background_image_url" to "http://abs.twimg.com/images/themes/theme1/bg.png",
    "profile_background_image_url_https" to "https://abs.twimg.com/images/themes/theme1/bg.png",
    "profile_background_tile" to false,
    "profile_image_url" to "http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png",
    "profile_image_url_https" to "https://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png",
    "profile_banner_url" to null,
    "profile_link_color" to "FFFFFF",
    "profile_sidebar_border_color" to "000000",
    "profile_sidebar_fill_color" to "000000",
    "profile_text_color" to "000000",
    "profile_use_background_image" to false,
    "has_extended_profile" to false,
    "default_profile" to false,
    "default_profile_image" to false,
    "following" to false,
    "follow_request_sent" to false,
    "notifications" to false,
    "contributors_enabled" to false
) {
    companion object {
        private const val userId = 1L
    }

    /**
     * Sets name.
     */
    fun name(value: String) {
        this["name"] = value
    }

    /**
     * Sets screen_name
     */
    fun screenName(value: String) {
        this["screen_name"] = value
    }

    /**
     * Sets location.
     */
    fun location(value: String) {
        this["location"] = value
    }

    /**
     * Sets protected.
     */
    fun isProtected() {
        this["protected"] = true
    }

    /**
     * Sets verified.
     */
    fun isVerified() {
        this["verified"] = true
    }

    /**
     * Sets count.
     */
    fun count(friends: Int = 0, followers: Int = 0, statuses: Int = 0, favorites: Int = 0, listed: Int = 0) {
        this["friends_count"] = friends
        this["followers_count"] = followers
        this["statuses_count"] = statuses
        this["favourites_count"] = favorites
        this["listed_count"] = listed
    }

    /**
     * Sets icon.
     */
    fun icon(url: String) {
        this["profile_image_url"] = url.replace("https://", "http://")
        this["profile_image_url_https"] = url.replace("http://", "https://")
    }

    private var createdAt: TemporalAccessor? = null
    /**
     * Sets created_at.
     */
    fun createdAt(time: TemporalAccessor? = null) {
        createdAt = time
    }

    override fun build(): User {
        this["created_at"] = createdAt.toCreatedAt()

        return toJsonObject().parseObject { User(it, NoopApiClient) }
    }
}

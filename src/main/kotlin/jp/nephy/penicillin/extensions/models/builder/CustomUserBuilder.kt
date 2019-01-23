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

import jp.nephy.jsonkt.jsonArrayOf
import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.models.User
import java.util.*

class CustomUserBuilder: JsonBuilder<User> {
    companion object {
        private const val userId = 1L
    }

    override var json = jsonObjectOf(
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
    )

    fun name(value: String) {
        update { 
            it["name"] = value 
        }
    }
    fun screenName(value: String) {
        update {
            it["screen_name"] = value
        }
    }

    fun location(value: String) {
        update {
            it["location"] = value
        }
    }

    fun isProtected() {
        update {
            it["protected"] = true
        }
    }
    fun isVerified() {
        update {
            it["verified"] = true
        }
    }

    fun count(friends: Int = 0, followers: Int = 0, statuses: Int = 0, favorites: Int = 0, listed: Int = 0) {
        update {
            it["friends_count"] = friends
            it["followers_count"] = followers
            it["statuses_count"] = statuses
            it["favourites_count"] = favorites
            it["listed_count"] = listed
        }
    }

    fun icon(url: String) {
        update {
            it["profile_image_url"] = url.replace("https://", "http://")
            it["profile_image_url_https"] = url.replace("http://", "https://")
        }
    }

    private var createdAt: Date? = null
    fun createdAt(date: Date? = null) {
        createdAt = date
    }

    @UseExperimental(PenicillinExperimentalApi::class)
    override fun build(): User {
        update {
            it["created_at"] = createdAt.toCreatedAt()
        }

        return json.parseModel()
    }
}

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
import blue.starry.jsonkt.delegation.boolean
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.model
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient
data class Relationship(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val source by model { Source(it, client) }
    val target by model { Target(it, client) }

    data class Source(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val allReplies by boolean("all_replies")
        val blockedBy by boolean("blocked_by")
        val blocking by boolean
        val canDm by boolean("can_dm")
        val canMediaTag by boolean("can_media_tag")
        val followedBy by boolean("followed_by")
        val following by boolean
        val followingReceived by boolean("following_received")
        val followingRequested by boolean("following_requested")
        val id by long
        val idStr by string("id_str")
        val liveFollowing by boolean("live_following")
        val markedSpam by boolean("marked_spam")
        val muting by boolean
        val notificationsEnabled by boolean("notifications_enabled")
        val screenName by string("screen_name")
        val wantRetweets by boolean("want_retweets")
    }

    data class Target(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val followedBy by boolean("followed_by")
        val following by boolean
        val followingReceived by boolean("following_received")
        val followingRequested by boolean("following_requested")
        val id by long
        val idStr by string("id_str")
        val screenName by string("screen_name")
    }
}
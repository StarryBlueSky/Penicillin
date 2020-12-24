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
import blue.starry.jsonkt.delegation.boolean
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.model
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient
public data class Relationship(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val source: Source by model { Source(it, client) }
    public val target: Target by model { Target(it, client) }

    public data class Source(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val allReplies: Boolean by boolean("all_replies")
        public val blockedBy: Boolean by boolean("blocked_by")
        public val blocking: Boolean by boolean
        public val canDm: Boolean by boolean("can_dm")
        public val canMediaTag: Boolean by boolean("can_media_tag")
        public val followedBy: Boolean by boolean("followed_by")
        public val following: Boolean by boolean
        public val followingReceived: Boolean by boolean("following_received")
        public val followingRequested: Boolean by boolean("following_requested")
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val liveFollowing: Boolean by boolean("live_following")
        public val markedSpam: Boolean by boolean("marked_spam")
        public val muting: Boolean by boolean
        public val notificationsEnabled: Boolean by boolean("notifications_enabled")
        public val screenName: String by string("screen_name")
        public val wantRetweets: Boolean by boolean("want_retweets")
    }

    public data class Target(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val followedBy: Boolean by boolean("followed_by")
        public val following: Boolean by boolean
        public val followingReceived: Boolean by boolean("following_received")
        public val followingRequested: Boolean by boolean("following_requested")
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val screenName: String by string("screen_name")
    }
}

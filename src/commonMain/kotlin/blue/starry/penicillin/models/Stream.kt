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
public object Stream {
    public abstract class Event(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
        public val event: String by string
        public val source: User by model { User(it, client) }
        public val target: User by model { User(it, client) }
        public val createdAtRaw: String by string("created_at")
    }

    public data class UserEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient)

    public data class StatusEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient) {
        public val targetObject: Status by model("target_object") { Status(it, client) }
    }

    public data class ListEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient) {
        public val targetObject: TwitterList by model("target_object") { TwitterList(it, client) }
    }

    public data class Friends(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val friends: List<Long?> by nullableLongList
        public val friendsStr: List<String?> by nullableStringList
    }

    public data class ScrubGeo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val scrubGeo by jsonObject("scrub_geo")
        public val userId: Long by scrubGeo.byLong("user_id")
        public val userIdStr: String by scrubGeo.byString("user_id_str")
        public val upToStatusId: Long by scrubGeo.byLong("up_to_status_id")
        public val upToStatusIdStr: String by scrubGeo.byString("up_to_status_id_str")
        public val timestampMs: String by scrubGeo.byString("timestamp_ms")
    }

    public data class StatusWithheld(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val statusWithheld by jsonObject("status_withheld")
        public val userId: Long by statusWithheld.byLong("user_id")
        public val id: Long by statusWithheld.byLong
        public val timestampMs: String by statusWithheld.byString("timestamp_ms")
        public val withheldInCountries: List<String> by statusWithheld.byStringList("withheld_in_countries")
    }

    public data class UserWithheld(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val userWithheld by jsonObject("user_withheld")
        public val id: Long by userWithheld.byLong
        public val withheldInCountries: List<String> by userWithheld.byStringList("withheld_in_countries")
    }

    public data class Disconnect(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val disconnect by jsonObject
        public val code: Int by disconnect.byInt
        public val streamName: String? by disconnect.byNullableString("stream_name")
        public val reason: String by disconnect.byString
    }

    public data class Limit(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val limit by jsonObject
        public val track: Int by limit.byInt
        public val timestampMs: String by limit.byString("timestamp_ms")
    }

    public data class Warning(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val warning by jsonObject
        public val code: Int by warning.byInt
        public val message: String by warning.byString
        public val percentFull: Int? by warning.byNullableInt("percent_full")
        public val userId: Long? by warning.byNullableLong
    }
    
    public data class Delete(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val delete by jsonObject
        private val status by delete.byJsonObject
        public val timestampMs: String by delete.byString("timestamp_ms")
        public val statusId: Long by status.byLong("id")
        public val statusIdStr: String by status.byString("id_str")
        public val userId: Long by status.byLong("user_id")
        public val userIdStr: String by status.byString("user_id_str")
    }
    
    public data class LivePipeline(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val topic: String by string
        public val payload: Payload by model { Payload(it, client) }
        
        public data class Payload(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val tweetEngagement: TweetEngagement by model("tweet_engagement") { TweetEngagement(it, client) }
            
            public data class TweetEngagement(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val likeCount: Int? by nullableInt("like_count")
                public val retweetCount: Int? by nullableInt("retweet_count")
                public val replyCount: Int? by nullableInt("reply_count")
            }
        }
    }
}

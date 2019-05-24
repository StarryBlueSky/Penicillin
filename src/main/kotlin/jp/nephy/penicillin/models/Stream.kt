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
import jp.nephy.penicillin.extensions.penicillinModel

object Stream {
    abstract class Event(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
        val event by string
        val source by penicillinModel<User>()
        val target by penicillinModel<User>()
        val createdAtRaw by string("created_at")
    }

    data class UserEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient)

    data class StatusEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient) {
        val targetObject by penicillinModel<Status>("target_object")
    }

    data class ListEvent(private val parentJson: JsonObject, private val parentClient: ApiClient): Event(parentJson, parentClient) {
        val targetObject by penicillinModel<TwitterList>("target_object")
    }

    data class Friends(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val friends by nullableLongList()
        val friendsStr by nullableStringList
    }

    data class ScrubGeo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val scrubGeo by jsonObject("scrub_geo")
        val userId by scrubGeo.byLong("user_id")
        val userIdStr by scrubGeo.byString("user_id_str")
        val upToStatusId by scrubGeo.byLong("up_to_status_id")
        val upToStatusIdStr by scrubGeo.byString("up_to_status_id_str")
        val timestampMs by scrubGeo.byString("timestamp_ms")
    }

    data class StatusWithheld(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val statusWithheld by jsonObject("status_withheld")
        val userId by statusWithheld.byLong("user_id")
        val id by statusWithheld.byLong
        val timestampMs by statusWithheld.byString("timestamp_ms")
        val withheldInCountries by statusWithheld.byStringList("withheld_in_countries")
    }

    data class UserWithheld(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val userWithheld by jsonObject("user_withheld")
        val id by userWithheld.byLong
        val withheldInCountries by userWithheld.byStringList("withheld_in_countries")
    }

    data class Disconnect(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val disconnect by jsonObject
        val code by disconnect.byInt
        val streamName by disconnect.byNullableString("stream_name")
        val reason by disconnect.byString
    }

    data class Limit(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val limit by jsonObject
        val track by limit.byInt
        val timestampMs by limit.byString("timestamp_ms")
    }

    data class Warning(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val warning by jsonObject
        val code by warning.byInt
        val message by warning.byString
        val percentFull by warning.byNullableInt("percent_full")
        val userId by warning.byNullableLong
    }
    
    data class Delete(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val delete by jsonObject
        private val status by delete.byJsonObject
        val timestampMs by delete.byString("timestamp_ms")
        val statusId by status.byLong("id")
        val statusIdStr by status.byString("id_str")
        val userId by status.byLong("user_id")
        val userIdStr by status.byString("user_id_str")
    }
    
    data class LivePipeline(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val topic by string
        val payload by penicillinModel<Payload>()
        
        data class Payload(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val tweetEngagement by penicillinModel<TweetEngagement>("tweet_engagement")
            
            data class TweetEngagement(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val likeCount by nullableInt("like_count")
                val retweetCount by nullableInt("retweet_count")
                val replyCount by nullableInt("reply_count") 
            }
        }
    }
}

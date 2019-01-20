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

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.streaming.handler.FilterStreamHandler
import jp.nephy.penicillin.core.streaming.handler.SampleStreamHandler
import jp.nephy.penicillin.core.streaming.handler.UserStreamHandler
import jp.nephy.penicillin.core.streaming.listener.FilterStreamListener
import jp.nephy.penicillin.core.streaming.listener.SampleStreamListener
import jp.nephy.penicillin.core.streaming.listener.UserStreamListener
import jp.nephy.penicillin.endpoints.parameters.StreamDelimitedBy
import jp.nephy.penicillin.endpoints.parameters.UserStreamFilterLevel
import jp.nephy.penicillin.endpoints.parameters.UserStreamReplies
import jp.nephy.penicillin.endpoints.parameters.UserStreamWith

val PenicillinClient.stream: Stream
    get() = Stream(this)

class Stream(override val client: PenicillinClient): Endpoint {
    @Deprecated("UserStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
    fun user(
        delimited: StreamDelimitedBy? = null,
        stallWarnings: Boolean? = null,
        with: UserStreamWith? = null,
        replies: UserStreamReplies? = null,
        track: List<String>? = null,
        filterLevel: UserStreamFilterLevel? = null,
        language: String? = null,
        follow: List<Long>? = null,
        locations: Pair<Float, Float>? = null,
        count: Int? = null,
        includeFollowingsActivity: Boolean? = null,
        stringifyFriendIds: Boolean? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/user.json", EndpointHost.UserStream) {
        parameter(
            "delimited" to delimited?.value,
            "stall_warning" to stallWarnings,
            "with" to with?.value,
            "replies" to replies?.value,
            "track" to track?.joinToString(","),
            "filter_level" to filterLevel?.value,
            "language" to language,
            "follow" to follow?.joinToString(","),
            "locations" to locations?.toList()?.joinToString(","),
            "count" to count,
            "include_followings_activity" to includeFollowingsActivity,
            "stringify_friend_ids" to stringifyFriendIds,
            *options
        )
    }.stream<UserStreamListener, UserStreamHandler>()

    @Deprecated("SiteStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
    fun site(
        delimited: StreamDelimitedBy? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, follow: List<Long>? = null, vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/site.json", EndpointHost.SiteStream) {
        parameter(
            "delimited" to delimited?.value,
            "stall_warning" to stallWarnings,
            "with" to with?.value,
            "replies" to replies?.value,
            "follow" to follow?.joinToString(","),
            *options
        )
    }.stream<UserStreamListener, UserStreamHandler>()

    fun sample(delimited: StreamDelimitedBy? = null, stallWarnings: Boolean? = null, language: String? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/statuses/sample.json", EndpointHost.Stream) {
            parameter(
                "delimited" to delimited?.value,
                "stall_warning" to stallWarnings,
                "language" to language,
                *options
            )
        }.stream<SampleStreamListener, SampleStreamHandler>()

    fun filter(
        delimited: StreamDelimitedBy? = null,
        stallWarnings: Boolean? = null,
        track: List<String>? = null,
        follow: List<Long>? = null,
        locations: Pair<Float, Float>? = null,
        language: String? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/statuses/filter.json", EndpointHost.Stream) {
        parameter(
            "delimited" to delimited?.value,
            "stall_warning" to stallWarnings,
            "track" to track?.joinToString(","),
            "follow" to follow?.joinToString(","),
            "locations" to locations?.toList()?.joinToString(","),
            "language" to language,
            *options
        )
    }.stream<FilterStreamListener, FilterStreamHandler>()
}

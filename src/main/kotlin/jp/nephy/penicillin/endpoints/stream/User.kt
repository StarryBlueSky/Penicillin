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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.stream


import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.request.action.StreamApiAction
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.streaming.handler.UserStreamHandler
import jp.nephy.penicillin.core.streaming.listener.UserStreamListener
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Stream
import jp.nephy.penicillin.endpoints.parameters.StreamDelimitedBy
import jp.nephy.penicillin.endpoints.parameters.UserStreamFilterLevel
import jp.nephy.penicillin.endpoints.parameters.UserStreamReplies
import jp.nephy.penicillin.endpoints.parameters.UserStreamWith

/**
 * Returns realtime timeline.
 * 
 * @param options Optional. Custom parameters of this request.
 * @receiver [Stream] endpoint instance.
 * @return [StreamApiAction] for [UserStreamHandler] handler with [UserStreamListener] listener.
 */
@Deprecated("UserStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
fun Stream.user(
    delimited: StreamDelimitedBy? = null,
    stallWarnings: Boolean? = null,
    with: UserStreamWith? = null,
    replies: UserStreamReplies? = null,
    track: List<String>? = null,
    filterLevel: UserStreamFilterLevel? = null,
    language: String? = null,
    follow: List<Long>? = null,
    locations: List<Pair<Double, Double>>? = null,
    count: Int? = null,
    includeFollowingsActivity: Boolean? = null,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
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
        "locations" to locations?.joinToString(",") { "${it.first},${it.second}" },
        "count" to count,
        "include_followings_activity" to includeFollowingsActivity,
        "stringify_friend_ids" to stringifyFriendIds,
        *options
    )
}.stream<UserStreamListener, UserStreamHandler>()

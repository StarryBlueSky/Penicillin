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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.stream


import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.StreamApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.core.streaming.handler.UserStreamHandler
import blue.starry.penicillin.core.streaming.listener.UserStreamListener
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Stream

/**
 * Returns realtime timeline.
 * 
 * @param options Optional. Custom parameters of this request.
 * @receiver [Stream] endpoint instance.
 * @return [StreamApiAction] for [UserStreamHandler] handler with [UserStreamListener] listener.
 */
@Deprecated("UserStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
fun Stream.user(
    delimited: StreamDelimitedBy = StreamDelimitedBy.Default,
    stallWarnings: Boolean? = null,
    with: UserStreamWith = UserStreamWith.Default,
    replies: UserStreamReplies = UserStreamReplies.Default,
    track: List<String>? = null,
    filterLevel: UserStreamFilterLevel = UserStreamFilterLevel.Default,
    language: String? = null,
    follow: List<Long>? = null,
    locations: List<Pair<Double, Double>>? = null,
    count: Int? = null,
    includeFollowingsActivity: Boolean? = null,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/user.json", EndpointHost.UserStream) {
    parameters(
        "delimited" to delimited,
        "stall_warning" to stallWarnings,
        "with" to with,
        "replies" to replies,
        "track" to track?.joinToString(","),
        "filter_level" to filterLevel,
        "language" to language,
        "follow" to follow?.joinToString(","),
        "locations" to locations?.joinToString(",") { "${it.first},${it.second}" },
        "count" to count,
        "include_followings_activity" to includeFollowingsActivity,
        "stringify_friend_ids" to stringifyFriendIds,
        *options
    )
}.stream<UserStreamListener, UserStreamHandler>()

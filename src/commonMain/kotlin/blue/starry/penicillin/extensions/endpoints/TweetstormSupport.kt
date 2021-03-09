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

@file:Suppress("UNUSED")

package blue.starry.penicillin.extensions.endpoints

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.parseObject
import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.StreamApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.core.streaming.handler.StreamHandler
import blue.starry.penicillin.core.streaming.listener.StreamListener
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Stream
import blue.starry.penicillin.endpoints.stream.StreamDelimitedBy
import blue.starry.penicillin.models.DirectMessage
import blue.starry.penicillin.models.Status

/**
 * Connects to Tweetstorm with custom [EndpointHost].
 *
 * @param customHost Custom host to Tweetstorm.
 * @param options Optional. Custom parameters of this request.
 */
public fun Stream.tweetstorm(
    customHost: EndpointHost = EndpointHost.UserStream,
    delimited: StreamDelimitedBy = StreamDelimitedBy.Default,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
): StreamApiAction<TweetstormListener, TweetstormHandler> = client.session.get("/1.1/user.json", customHost) {
    parameters(
        "delimited" to delimited,
        "stringify_friend_ids" to stringifyFriendIds,
        *options
    )
}.stream()

/**
 * Connects to Tweetstorm with custom host.
 *
 * @param customHost Custom host to Tweetstorm.
 * @param options Optional. Custom parameters of this request.
 */
public fun Stream.tweetstorm(
    customHost: String,
    delimited: StreamDelimitedBy = StreamDelimitedBy.Default,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
): StreamApiAction<TweetstormListener, TweetstormHandler> = tweetstorm(EndpointHost(customHost, domainForSigning = EndpointHost.UserStream.domain), delimited, stringifyFriendIds, *options)

/**
 * Shorthand property to [Stream.tweetstorm].
 * @see Stream.tweetstorm
 */
public val Stream.tweetstorm: StreamApiAction<TweetstormListener, TweetstormHandler>
    get() = tweetstorm()

/**
 * An event model interface for [TweetstormHandler].
 */
public interface TweetstormListener: StreamListener {
    /**
     * Called when a status is received.
     */
    public suspend fun onStatus(status: Status) {}
    /**
     * Called when a direct message is received.
     */
    public suspend fun onDirectMessage(message: DirectMessage) {}

    /**
     * Called when a friends event is received.
     */
    public suspend fun onFriends(friends: blue.starry.penicillin.models.Stream.Friends) {}

    /**
     * Called when a delete event is received.
     */
    public suspend fun onDelete(delete: blue.starry.penicillin.models.Stream.Delete) {}
}

/**
 * Default TweetStorm [StreamHandler].
 * Accepts listener of [TweetstormListener].
 */
public class TweetstormHandler(override val client: ApiClient, override val listener: TweetstormListener): StreamHandler<TweetstormListener> {
    override suspend fun handle(json: JsonObject) {
        when {
            "text" in json -> {
                listener.onStatus(json.parseObject { Status(it, client) })
            }
            "direct_message" in json -> {
                listener.onDirectMessage(json.parseObject { DirectMessage(it, client) })
            }
            "friends" in json -> {
                listener.onFriends(json.parseObject { blue.starry.penicillin.models.Stream.Friends(it, client) })
            }
            "delete" in json -> {
                listener.onDelete(json.parseObject { blue.starry.penicillin.models.Stream.Delete(it, client) })
            }
            else -> {
                listener.onUnhandledJson(json)
            }
        }

        listener.onAnyJson(json)
    }
}

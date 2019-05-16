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

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Stream
import jp.nephy.penicillin.endpoints.stream.StreamDelimitedBy
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import kotlinx.coroutines.launch

/**
 * Connects to Tweetstorm with custom [EndpointHost].
 *
 * @param customHost Custom host to Tweetstorm.
 * @param options Optional. Custom parameters of this request.
 */
fun Stream.tweetstorm(
    customHost: EndpointHost = EndpointHost.UserStream,
    delimited: StreamDelimitedBy = StreamDelimitedBy.Default,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/user.json", customHost) {
    parameter(
        "delimited" to delimited.value,
        "stringify_friend_ids" to stringifyFriendIds,
        *options
    )
}.stream<TweetstormListener, TweetstormHandler>()

/**
 * Connects to Tweetstorm with custom host.
 *
 * @param customHost Custom host to Tweetstorm.
 * @param options Optional. Custom parameters of this request.
 */
fun Stream.tweetstorm(
    customHost: String,
    delimited: StreamDelimitedBy = StreamDelimitedBy.Default,
    stringifyFriendIds: Boolean? = null,
    vararg options: Option
) = tweetstorm(EndpointHost(customHost, domainForSigning = EndpointHost.UserStream.domain), delimited, stringifyFriendIds, *options)

/**
 * Shorthand property to [Stream.tweetstorm].
 * @see Stream.tweetstorm
 */
val Stream.tweetstorm
    get() = tweetstorm()

/**
 * An event model interface for [TweetstormHandler].
 */
interface TweetstormListener: StreamListener {
    /**
     * Called when a status is received.
     */
    suspend fun onStatus(status: Status) {}
    /**
     * Called when a direct message is received.
     */
    suspend fun onDirectMessage(message: DirectMessage) {}

    /**
     * Called when a friends event is received.
     */
    suspend fun onFriends(friends: jp.nephy.penicillin.models.Stream.Friends) {}

    /**
     * Called when a delete event is received.
     */
    suspend fun onDelete(delete: jp.nephy.penicillin.models.Stream.Delete) {}
}

/**
 * Default TweetStorm [StreamHandler].
 * Accepts listener of [TweetstormListener].
 */
class TweetstormHandler(override val client: ApiClient, override val listener: TweetstormListener): StreamHandler<TweetstormListener> {
    override fun handle(json: JsonObject) {
        launch {
            when {
                "text" in json -> {
                    listener.onStatus(json.parseModel(client))
                }
                "direct_message" in json -> {
                    listener.onDirectMessage(json.parseModel(client))
                }
                "friends" in json -> {
                    listener.onFriends(json.parseModel(client))
                }
                "delete" in json -> {
                    listener.onDelete(json.parseModel(client))
                }
                else -> {
                    listener.onUnhandledJson(json)
                }
            }
        }

        launch {
            listener.onAnyJson(json)
        }
    }
}

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

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import jp.nephy.penicillin.endpoints.Stream
import jp.nephy.penicillin.endpoints.parameters.StreamDelimitedBy
import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.UserStream
import jp.nephy.penicillin.models.parsePenicillinModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Stream.tweetstorm(
    customHost: EndpointHost = EndpointHost.UserStream, delimited: StreamDelimitedBy? = null, stringifyFriendIds: Boolean? = null, vararg options: Pair<String, Any?>
) = client.session.get("/1.1/user.json", customHost) {
    parameter(
        "delimited" to delimited?.value,
        "stringify_friend_ids" to stringifyFriendIds,
        *options
    )
}.stream<TweetstormListener, TweetstormHandler>()

fun Stream.tweetstorm(
    customHost: String, delimited: StreamDelimitedBy? = null, stringifyFriendIds: Boolean? = null, vararg options: Pair<String, Any?>
) = tweetstorm(EndpointHost(customHost, domainForSigning = EndpointHost.UserStream.domain), delimited, stringifyFriendIds, *options)

interface TweetstormListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDirectMessage(message: DirectMessage) {}
    
    suspend fun onFriends(friends: UserStream.Friends) {}

    suspend fun onDelete(delete: jp.nephy.penicillin.models.Stream.Delete) {}
}

class TweetstormHandler(override val client: PenicillinClient, override val listener: TweetstormListener): StreamHandler<TweetstormListener> {
    override suspend fun handle(json: JsonObject, scope: CoroutineScope) {
        scope.launch {
            when {
                "text" in json -> {
                    listener.onStatus(client.parsePenicillinModel(json))
                }
                "direct_message" in json -> {
                    listener.onDirectMessage(client.parsePenicillinModel(json))
                }
                "friends" in json -> {
                    listener.onFriends(client.parsePenicillinModel(json))
                }
                "delete" in json -> {
                    listener.onDelete(client.parsePenicillinModel(json))
                }
                else -> {
                    listener.onUnhandledJson(json)
                }
            }
        }

        listener.onAnyJson(json)
    }
}

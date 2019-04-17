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

package jp.nephy.penicillin.core.streaming.handler

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.streaming.listener.UserStreamListener
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.models.Stream
import kotlinx.coroutines.launch

/**
 * Default UserStream [StreamHandler].
 * Accepts listener of [UserStreamListener].
 */
class UserStreamHandler(override val client: ApiClient, override val listener: UserStreamListener): StreamHandler<UserStreamListener> {
    override fun handle(json: JsonObject) {
        launch {
            when {
                "text" in json -> {
                    listener.onStatus(json.parseModel(client))
                }
                "direct_message" in json -> {
                    listener.onDirectMessage(json.parseModel(client))
                }
                "event" in json -> {
                    val event = UserStreamEvent.byKey(json["event"].string)
                    when (event?.type) {
                        UserStreamEventType.Status -> {
                            val statusEvent = json.parseModel<Stream.StatusEvent>(client)
                            launch {
                                when (event) {
                                    UserStreamEvent.Favorite -> listener.onFavorite(statusEvent)
                                    UserStreamEvent.Unfavorite -> listener.onUnfavorite(statusEvent)
                                    UserStreamEvent.FavoritedRetweet -> listener.onFavoritedRetweet(statusEvent)
                                    UserStreamEvent.RetweetedRetweet -> listener.onRetweetedRetweet(statusEvent)
                                    UserStreamEvent.QuotedTweet -> listener.onQuotedTweet(statusEvent)
                                    else -> listener.onUnhandledJson(json)
                                }
                            }
                            
                            launch {
                                listener.onAnyStatusEvent(statusEvent)
                            }
                            
                            listener.onAnyEvent(statusEvent)
                        }
                        UserStreamEventType.List -> {
                            val listEvent = json.parseModel<Stream.ListEvent>(client)
                            launch {
                                when (event) {
                                    UserStreamEvent.ListCreated -> listener.onListCreated(listEvent)
                                    UserStreamEvent.ListDestroyed -> listener.onListDestroyed(listEvent)
                                    UserStreamEvent.ListUpdated -> listener.onListUpdated(listEvent)
                                    UserStreamEvent.ListMemberAdded -> listener.onListMemberAdded(listEvent)
                                    UserStreamEvent.ListMemberRemoved -> listener.onListMemberRemoved(listEvent)
                                    UserStreamEvent.ListUserSubscribed -> listener.onListUserSubscribed(listEvent)
                                    UserStreamEvent.ListUserUnsubscribed -> listener.onListUserUnsubscribed(listEvent)
                                    else -> listener.onUnhandledJson(json)
                                }
                            }
                            
                            launch {
                                listener.onAnyListEvent(listEvent)
                            }
                            
                            listener.onAnyEvent(listEvent)
                        }
                        UserStreamEventType.User -> {
                            val userEvent = json.parseModel<Stream.UserEvent>(client)
                            launch {
                                when (event) {
                                    UserStreamEvent.Follow -> listener.onFollow(userEvent)
                                    UserStreamEvent.Unfollow -> listener.onUnfollow(userEvent)
                                    UserStreamEvent.Block -> listener.onBlock(userEvent)
                                    UserStreamEvent.Unblock -> listener.onUnblock(userEvent)
                                    UserStreamEvent.Mute -> listener.onMute(userEvent)
                                    UserStreamEvent.Unmute -> listener.onUnmute(userEvent)
                                    UserStreamEvent.UserUpdate -> listener.onUserUpdate(userEvent)
                                    else -> listener.onUnhandledJson(json)
                                }
                            }
                            
                            launch {
                                listener.onAnyUserEvent(userEvent)
                            }
                            
                            listener.onAnyEvent(userEvent)
                        }
                        else -> {
                            listener.onUnhandledJson(json)
                        }
                    }
                }
                "friends" in json -> {
                    listener.onFriends(json.parseModel(client))
                }
                "delete" in json -> {
                    listener.onDelete(json.parseModel(client))
                }
                "scrub_geo" in json -> {
                    listener.onScrubGeo(json.parseModel(client))
                }
                "status_withheld" in json -> {
                    listener.onStatusWithheld(json.parseModel(client))
                }
                "user_withheld" in json -> {
                    listener.onUserWithheld(json.parseModel(client))
                }
                "disconnect" in json -> {
                    listener.onDisconnectMessage(json.parseModel(client))
                }
                "warning" in json -> {
                    listener.onWarning(json.parseModel(client))
                }
                "limit" in json -> {
                    listener.onLimit(json.parseModel(client))
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

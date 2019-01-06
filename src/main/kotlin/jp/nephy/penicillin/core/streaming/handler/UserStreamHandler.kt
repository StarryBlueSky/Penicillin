package jp.nephy.penicillin.core.streaming.handler

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.core.streaming.listener.UserStreamListener
import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream
import jp.nephy.penicillin.models.UserStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserStreamHandler(override val listener: UserStreamListener): StreamHandler<UserStreamListener> {
    companion object {
        private val statusEvents = arrayOf("favorite", "unfavorite", "favorited_retweet", "retweeted_retweet", "quoted_tweet")
        private val listEvents = arrayOf("list_created", "list_destroyed", "list_updated", "list_member_added", "list_member_removed", "list_user_subscribed", "list_user_unsubscribed")
        private val userEvents = arrayOf("follow", "unfollow", "block", "unblock", "mute", "unmute", "user_update")
    }

    override suspend fun handle(json: JsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            when {
                "text" in json -> {
                    listener.onStatus(Status(json))
                }
                "direct_message" in json -> {
                    listener.onDirectMessage(DirectMessage(json))
                }
                "event" in json -> {
                    val event = json["event"].string
                    when (event) {
                        in statusEvents -> {
                            val statusEvent = UserStream.StatusEvent(json)
                            launch(scope.coroutineContext) {
                                when (event) {
                                    "favorite" -> listener.onFavorite(statusEvent)
                                    "unfavorite" -> listener.onUnfavorite(statusEvent)
                                    "favorited_retweet" -> listener.onFavoritedRetweet(statusEvent)
                                    "retweeted_retweet" -> listener.onRetweetedRetweet(statusEvent)
                                    "quoted_tweet" -> listener.onQuotedTweet(statusEvent)
                                }
                            }
                            launch(scope.coroutineContext) {
                                listener.onAnyStatusEvent(statusEvent)
                            }
                            listener.onAnyEvent(statusEvent)
                        }
                        in listEvents -> {
                            val listEvent = UserStream.ListEvent(json)
                            launch(scope.coroutineContext) {
                                when (event) {
                                    "list_created" -> listener.onListCreated(listEvent)
                                    "list_destroyed" -> listener.onListDestroyed(listEvent)
                                    "list_updated" -> listener.onListUpdated(listEvent)
                                    "list_member_added" -> listener.onListMemberAdded(listEvent)
                                    "list_member_removed" -> listener.onListMemberRemoved(listEvent)
                                    "list_user_subscribed" -> listener.onListUserSubscribed(listEvent)
                                    "list_user_unsubscribed" -> listener.onListUserUnsubscribed(listEvent)
                                }
                            }
                            launch(scope.coroutineContext) {
                                listener.onAnyListEvent(listEvent)
                            }
                            listener.onAnyEvent(listEvent)
                        }
                        in userEvents -> {
                            val userEvent = UserStream.UserEvent(json)
                            launch(scope.coroutineContext) {
                                when (event) {
                                    "follow" -> listener.onFollow(userEvent)
                                    "unfollow" -> listener.onUnfollow(userEvent)
                                    "block" -> listener.onBlock(userEvent)
                                    "unblock" -> listener.onUnblock(userEvent)
                                    "mute" -> listener.onMute(userEvent)
                                    "unmute" -> listener.onUnmute(userEvent)
                                    "user_update" -> listener.onUserUpdate(userEvent)
                                }
                            }
                            launch(scope.coroutineContext) {
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
                    listener.onFriends(UserStream.Friends(json))
                }
                "delete" in json -> {
                    listener.onDelete(Stream.Delete(json))
                }
                "scrub_geo" in json -> {
                    listener.onScrubGeo(UserStream.ScrubGeo(json))
                }
                "status_withheld" in json -> {
                    listener.onStatusWithheld(UserStream.StatusWithheld(json))
                }
                "user_withheld" in json -> {
                    listener.onUserWithheld(UserStream.UserWithheld(json))
                }
                "disconnect" in json -> {
                    listener.onDisconnectMessage(UserStream.Disconnect(json))
                }
                "warning" in json -> {
                    listener.onWarning(UserStream.Warning(json))
                }
                "limit" in json -> {
                    listener.onLimit(UserStream.Limit(json))
                }
                else -> {
                    listener.onUnhandledJson(json)
                }
            }
        }

        listener.onAnyJson(json)
    }
}


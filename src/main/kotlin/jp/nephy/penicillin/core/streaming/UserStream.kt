package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.*
import java.util.concurrent.ExecutorService

private val statusEvents = arrayOf("favorite", "unfavorite", "favorited_retweet", "retweeted_retweet", "quoted_tweet")
private val listEvents = arrayOf("list_created", "list_destroyed", "list_updated", "list_member_added", "list_member_removed", "list_user_subscribed", "list_user_unsubscribed")
private val userEvents = arrayOf("follow", "unfollow", "block", "unblock", "mute", "unmute", "user_update")

class UserStreamHandler(override val listener: UserStreamListener, override val executor: ExecutorService): StreamHandler<UserStreamListener> {
    override fun handle(json: JsonObject) {
        executor.execute { listener.onRawJson(json) }
        when {
            json.contains("text") -> listener.onStatus(Status(json))
            json.contains("direct_message") -> listener.onDirectMessage(DirectMessage(json))
            json.contains("event") -> {
                val event = json["event"].string
                when (event) {
                    in statusEvents -> {
                        val statusEvent = UserStreamStatusEvent(json)
                        executor.execute {
                            when (event) {
                                "favorite" -> listener.onFavorite(statusEvent)
                                "unfavorite" -> listener.onUnfavorite(statusEvent)
                                "favorited_retweet" -> listener.onFavoritedRetweet(statusEvent)
                                "retweeted_retweet" -> listener.onRetweetedRetweet(statusEvent)
                                "quoted_tweet" -> listener.onQuotedTweet(statusEvent)
                            }
                        }
                        executor.execute { listener.onAnyStatusEvent(statusEvent) }
                        listener.onAnyEvent(statusEvent)
                    }
                    in listEvents -> {
                        val listEvent = UserStreamListEvent(json)
                        executor.execute {
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
                        executor.execute { listener.onAnyListEvent(listEvent) }
                        listener.onAnyEvent(listEvent)
                    }
                    in userEvents -> {
                        val userEvent = UserStreamUserEvent(json)
                        executor.execute {
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
                        executor.execute { listener.onAnyUserEvent(userEvent) }
                        listener.onAnyEvent(userEvent)
                    }
                    else -> listener.onUnhandledData(json)
                }
            }
            json.contains("friends") -> listener.onFriends(UserStreamFriends(json))
            json.contains("delete") -> listener.onDelete(StreamDelete(json))
            json.contains("scrub_geo") -> listener.onScrubGeo(UserStreamScrubGeo(json))
            json.contains("status_withheld") -> listener.onStatusWithheld(UserStreamStatusWithheld(json))
            json.contains("limit") -> listener.onLimit(UserStreamLimit(json))
            else -> listener.onUnhandledData(json)
        }
    }
}

interface UserStreamListener: StreamListener {
    fun onStatus(status: Status) {}
    fun onDirectMessage(message: DirectMessage) {}

    fun onAnyEvent(event: UserStreamEvent) {}
    /* Status event */
    fun onAnyStatusEvent(event: UserStreamStatusEvent) {}

    fun onFavorite(event: UserStreamStatusEvent) {}
    fun onUnfavorite(event: UserStreamStatusEvent) {}
    fun onFavoritedRetweet(event: UserStreamStatusEvent) {}
    fun onRetweetedRetweet(event: UserStreamStatusEvent) {}
    fun onQuotedTweet(event: UserStreamStatusEvent) {}
    /* List event */
    fun onAnyListEvent(event: UserStreamListEvent) {}

    fun onListCreated(event: UserStreamListEvent) {}
    fun onListDestroyed(event: UserStreamListEvent) {}
    fun onListUpdated(event: UserStreamListEvent) {}
    fun onListMemberAdded(event: UserStreamListEvent) {}
    fun onListMemberRemoved(event: UserStreamListEvent) {}
    fun onListUserSubscribed(event: UserStreamListEvent) {}
    fun onListUserUnsubscribed(event: UserStreamListEvent) {}
    /* User event */
    fun onAnyUserEvent(event: UserStreamUserEvent) {}

    fun onFollow(event: UserStreamUserEvent) {}
    fun onUnfollow(event: UserStreamUserEvent) {}
    fun onBlock(event: UserStreamUserEvent) {}
    fun onUnblock(event: UserStreamUserEvent) {}
    fun onMute(event: UserStreamUserEvent) {}
    fun onUnmute(event: UserStreamUserEvent) {}
    fun onUserUpdate(event: UserStreamUserEvent) {}

    /* Misc */
    fun onFriends(friends: UserStreamFriends) {}

    fun onDelete(delete: StreamDelete) {}
    fun onScrubGeo(scrubGeo: UserStreamScrubGeo) {}
    fun onStatusWithheld(withheld: UserStreamStatusWithheld) {}
    fun onLimit(limit: UserStreamLimit) {}
}

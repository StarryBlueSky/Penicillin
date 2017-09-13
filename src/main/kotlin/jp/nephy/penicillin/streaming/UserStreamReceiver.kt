package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.response.ResponseStream

class UserStreamReceiver(response: ResponseStream, private val listener: IUserStreamListener): AbsStreamingParser(response) {
    override fun handle(json: JsonObject) = when {
        json.has("text") -> listener.onStatus(Status(json))
        json.has("direct_message") -> listener.onDirectMessage(DirectMessage(json))
        json.has("event") -> when (json["event"].asString) {
            "favorite" -> listener.onFavorite(StatusEvent(json))
            "unfavorite" -> listener.onUnfavorite(StatusEvent(json))
            "favorited_retweet" -> listener.onFavorite(StatusEvent(json))
            "retweeted_retweet" -> listener.onRetweetedRetweet(StatusEvent(json))
            "quoted_tweet" -> listener.onQuotedTweet(StatusEvent(json))
            "list_created" -> listener.onListCreated(ListEvent(json))
            "list_destroyed" -> listener.onListDestroyed(ListEvent(json))
            "list_updated" -> listener.onListUpdated(ListEvent(json))
            "list_member_added" -> listener.onListMemberAdded(ListEvent(json))
            "list_member_removed" -> listener.onListMemberRemoved(ListEvent(json))
            "list_user_subscribed" -> listener.onListUserSubscribed(ListEvent(json))
            "list_user_unsubscribed" -> listener.onListUserUnsubscribed(ListEvent(json))
            "follow" -> listener.onFollow(UserEvent(json))
            "unfollow" -> listener.onUnfollow(UserEvent(json))
            "block" -> listener.onBlock(UserEvent(json))
            "unblock" -> listener.onUnblock(UserEvent(json))
            "mute" -> listener.onMute(UserEvent(json))
            "unmute" -> listener.onUnmute(UserEvent(json))
            else -> listener.onUnknownData(json.toString())
        }
        json.has("friends") -> listener.onFriends(Friends(json))
        json.has("delete") -> listener.onDelete(Delete(json))
        json.has("scrub_geo") -> listener.onScrubGeo(ScrubGeo(json))
        json.has("status_withheld") -> listener.onStatusWithheld(StatusWithheld(json))
        json.has("limit") -> listener.onLimit(Limit(json))
        else -> listener.onUnknownData(json.toString())
    }
}

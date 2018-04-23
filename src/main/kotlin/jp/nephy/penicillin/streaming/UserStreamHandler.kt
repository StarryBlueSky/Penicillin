package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.model.*
import okhttp3.Response

class UserStreamHandler(response: Response, private val listener: UserStreamListener): StreamingParser(response, listener) {
    override fun handle(json: JsonObject) = when {
        json.contains("text") -> listener.onStatus(Status(json))
        json.contains("direct_message") -> listener.onDirectMessage(DirectMessage(json))
        json.contains("event") -> when (json["event"].string) {
            "favorite" -> listener.onFavorite(StatusEvent(json))
            "unfavorite" -> listener.onUnfavorite(StatusEvent(json))
            "favorited_retweet" -> listener.onFavoritedRetweet(StatusEvent(json))
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
            "user_update" -> listener.onUserUpdate(UserEvent(json))
            else -> listener.onUnknownData(json.toString())
        }
        json.contains("friends") -> listener.onFriends(Friends(json))
        json.contains("delete") -> listener.onDelete(Delete(json))
        json.contains("scrub_geo") -> listener.onScrubGeo(ScrubGeo(json))
        json.contains("status_withheld") -> listener.onStatusWithheld(StatusWithheld(json))
        json.contains("limit") -> listener.onLimit(Limit(json))
        else -> listener.onUnknownData(json.toString())
    }
}

package jp.nephy.penicillin.core.streaming.listener

import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream
import jp.nephy.penicillin.models.UserStream

interface UserStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDirectMessage(message: DirectMessage) {}

    suspend fun onAnyEvent(event: UserStream.Event) {}

    /* Status event */
    suspend fun onAnyStatusEvent(event: UserStream.StatusEvent) {}

    suspend fun onFavorite(event: UserStream.StatusEvent) {}
    suspend fun onUnfavorite(event: UserStream.StatusEvent) {}
    suspend fun onFavoritedRetweet(event: UserStream.StatusEvent) {}
    suspend fun onRetweetedRetweet(event: UserStream.StatusEvent) {}
    suspend fun onQuotedTweet(event: UserStream.StatusEvent) {}

    /* List event */
    suspend fun onAnyListEvent(event: UserStream.ListEvent) {}

    suspend fun onListCreated(event: UserStream.ListEvent) {}
    suspend fun onListDestroyed(event: UserStream.ListEvent) {}
    suspend fun onListUpdated(event: UserStream.ListEvent) {}
    suspend fun onListMemberAdded(event: UserStream.ListEvent) {}
    suspend fun onListMemberRemoved(event: UserStream.ListEvent) {}
    suspend fun onListUserSubscribed(event: UserStream.ListEvent) {}
    suspend fun onListUserUnsubscribed(event: UserStream.ListEvent) {}

    /* User event */
    suspend fun onAnyUserEvent(event: UserStream.UserEvent) {}

    suspend fun onFollow(event: UserStream.UserEvent) {}
    suspend fun onUnfollow(event: UserStream.UserEvent) {}
    suspend fun onBlock(event: UserStream.UserEvent) {}
    suspend fun onUnblock(event: UserStream.UserEvent) {}
    suspend fun onMute(event: UserStream.UserEvent) {}
    suspend fun onUnmute(event: UserStream.UserEvent) {}
    suspend fun onUserUpdate(event: UserStream.UserEvent) {}

    /* Misc */
    suspend fun onFriends(friends: UserStream.Friends) {}

    suspend fun onDelete(delete: Stream.Delete) {}
    suspend fun onScrubGeo(scrubGeo: UserStream.ScrubGeo) {}
    suspend fun onStatusWithheld(withheld: UserStream.StatusWithheld) {}
    suspend fun onUserWithheld(withheld: UserStream.UserWithheld) {}
    suspend fun onDisconnectMessage(disconnect: UserStream.Disconnect) {}
    suspend fun onWarning(warning: UserStream.Warning) {}
    suspend fun onLimit(limit: UserStream.Limit) {}
}

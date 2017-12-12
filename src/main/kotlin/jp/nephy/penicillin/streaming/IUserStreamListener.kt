package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.response.ResponseStream

interface IUserStreamListener: IListener<IUserStreamListener> {
    override fun getReceiver(response: ResponseStream<IUserStreamListener>) = UserStreamReceiver(response, this)

    /* Status */
    fun onStatus(status: Status) {}
    fun onDirectMessage(message: DirectMessage) {}

    /* Status event */
    fun onFavorite(event: StatusEvent) {}
    fun onUnfavorite(event: StatusEvent) {}
    fun onFavoritedRetweet(event: StatusEvent) {}
    fun onRetweetedRetweet(event: StatusEvent) {}
    fun onQuotedTweet(event: StatusEvent) {}

    /* List event */
    fun onListCreated(event: ListEvent) {}
    fun onListDestroyed(event: ListEvent) {}
    fun onListUpdated(event: ListEvent) {}
    fun onListMemberAdded(event: ListEvent) {}
    fun onListMemberRemoved(event: ListEvent) {}
    fun onListUserSubscribed(event: ListEvent) {}
    fun onListUserUnsubscribed(event: ListEvent) {}

    /* User event */
    fun onFollow(event: UserEvent) {}
    fun onUnfollow(event: UserEvent) {}
    fun onBlock(event: UserEvent) {}
    fun onUnblock(event: UserEvent) {}
    fun onMute(event: UserEvent) {}
    fun onUnmute(event: UserEvent) {}
    fun onUserUpdate(event: UserEvent) {}

    /* Misc */
    fun onFriends(friends: Friends) {}
    fun onDelete(delete: Delete) {}
    fun onScrubGeo(scrubGeo: ScrubGeo) {}
    fun onStatusWithheld(withheld: StatusWithheld) {}
    fun onLimit(limit: Limit) {}

    fun onUnknownData(data: String) {}
}

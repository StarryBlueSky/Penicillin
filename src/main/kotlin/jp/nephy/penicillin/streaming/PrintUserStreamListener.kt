package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.*

class PrintUserStreamListener : IUserStreamListener {
    override fun onStatus(status: Status) {
        println("Tweet: ${status.fullText()} by @${status.user.screenName}")
    }

    override fun onDirectMessage(message: DirectMessage) {
        println("DM: ${message.text} by @${message.sender.screenName}")
    }

    override fun onFavorite(event: StatusEvent) {
        println("Favorite: @${event.source.screenName} liked @${event.target.screenName}'s `${event.targetObject.fullText()}`.")
    }

    override fun onUnfavorite(event: StatusEvent) {
        println("Unfavorite: @${event.source.screenName} unliked @${event.target.screenName}'s `${event.targetObject.fullText()}`.")
    }

    override fun onFavoritedRetweet(event: StatusEvent) {
        println("Favorited Retweet: @${event.source.screenName} retweeted @${event.target.screenName}'s `${event.targetObject.fullText()}` that @${event.target.screenName} liked.")
    }

    override fun onRetweetedRetweet(event: StatusEvent) {
        println("Retweeted Retweet: @${event.source.screenName} retweeted @${event.target.screenName}'s `${event.targetObject.fullText()}` that @${event.target.screenName} retweeted too.")
    }

    override fun onQuotedTweet(event: StatusEvent) {
        println("Quoted: @${event.source.screenName} quoted @${event.target.screenName}'s `${event.targetObject.fullText()}`.")
    }

    override fun onListCreated(event: ListEvent) {
        println("List Created: @${event.source.screenName} created list `${event.targetObject.fullName}`.")
    }

    override fun onListDestroyed(event: ListEvent) {
        println("List Destroyed: @${event.source.screenName} destroyed list `${event.targetObject.fullName}`.")
    }

    override fun onListUpdated(event: ListEvent) {
        println("List Updated: @${event.source.screenName} updated list `${event.targetObject.fullName}`.")
    }

    override fun onListMemberAdded(event: ListEvent) {
        println("List Member Added: @${event.source.screenName} added @${event.target.screenName} to list `${event.targetObject.fullName}`.")
    }

    override fun onListMemberRemoved(event: ListEvent) {
        println("List Member Removed: @${event.source.screenName} removed @${event.target.screenName} from list `${event.targetObject.fullName}`.")
    }

    override fun onListUserSubscribed(event: ListEvent) {
        println("List Subscribed: @${event.source.screenName} subscribed @${event.target.screenName}'s list `${event.targetObject.fullName}`.")
    }

    override fun onListUserUnsubscribed(event: ListEvent) {
        println("List Unsubscribed: @${event.source.screenName} unsubscribed @${event.target.screenName}'s list `${event.targetObject.fullName}`.")
    }

    override fun onFollow(event: UserEvent) {
        println("Follow: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onUnfollow(event: UserEvent) {
        println("Unfollow: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onBlock(event: UserEvent) {
        println("Block: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onUnblock(event: UserEvent) {
        println("Unblock: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onMute(event: UserEvent) {
        println("Mute: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onUnmute(event: UserEvent) {
        println("Unmute: @${event.source.screenName} -> @${event.target.screenName}")
    }

    override fun onUserUpdate(event: UserEvent) {
        println("User Update: @${event.source.screenName}")
    }

    override fun onFriends(friends: Friends) {
        println("Friends: ${friends.friends.joinToString(", ")}")
    }

    override fun onDelete(delete: Delete) {
        println("Delete: User ID: ${delete.userId} deleted Status ID: ${delete.statusId}.")
    }

    override fun onScrubGeo(scrubGeo: ScrubGeo) {
        println("Scrub Geo: User ID: ${scrubGeo.userId} deleted geo data up to ${scrubGeo.upToStatusId}.")
    }

    override fun onStatusWithheld(withheld: StatusWithheld) {
        println("Status Withheld: User ID: ${withheld.userId}'s Status ID: ${withheld.id} has been withheld in ${withheld.withheldInCountries.joinToString(", ")}.")
    }

    override fun onLimit(limit: Limit) {
        println("Limit: Track ${limit.track}")
    }

    override fun onUnknownData(data: String) {
        println("Unknown Data: $data")
    }
}
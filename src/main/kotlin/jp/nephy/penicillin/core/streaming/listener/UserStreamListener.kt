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

package jp.nephy.penicillin.core.streaming.listener

import jp.nephy.penicillin.core.streaming.handler.UserStreamHandler
import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream

/**
 * An event model interface for [UserStreamHandler].
 */
interface UserStreamListener: StreamListener {
    /**
     * Called when a status is received.
     */
    suspend fun onStatus(status: Status) {}
    /**
     * Called when a direct message is received.
     */
    suspend fun onDirectMessage(message: DirectMessage) {}

    /**
     * Called when any events are received.
     */
    suspend fun onAnyEvent(event: Stream.Event) {}

    /* Status event */

    /**
     * Called when any status events are received.
     */
    suspend fun onAnyStatusEvent(event: Stream.StatusEvent) {}

    /**
     * Called when a favorite event is received.
     */
    suspend fun onFavorite(event: Stream.StatusEvent) {}
    /**
     * Called when an unfavorite event is received.
     */
    suspend fun onUnfavorite(event: Stream.StatusEvent) {}
    /**
     * Called when a favorited-retweet event is received.
     */
    suspend fun onFavoritedRetweet(event: Stream.StatusEvent) {}
    /**
     * Called when a retweeted-retweet event is received.
     */
    suspend fun onRetweetedRetweet(event: Stream.StatusEvent) {}
    /**
     * Called when a quoted-tweet event is received.
     */
    suspend fun onQuotedTweet(event: Stream.StatusEvent) {}

    /* List event */

    /**
     * Called when any list events are received.
     */
    suspend fun onAnyListEvent(event: Stream.ListEvent) {}

    /**
     * Called when a list-created event is received.
     */
    suspend fun onListCreated(event: Stream.ListEvent) {}
    /**
     * Called when a list-destroyed event is received.
     */
    suspend fun onListDestroyed(event: Stream.ListEvent) {}
    /**
     * Called when a list-updated event is received.
     */
    suspend fun onListUpdated(event: Stream.ListEvent) {}
    /**
     * Called when a list-member-added event is received.
     */
    suspend fun onListMemberAdded(event: Stream.ListEvent) {}
    /**
     * Called when a list-member-removed event is received.
     */
    suspend fun onListMemberRemoved(event: Stream.ListEvent) {}
    /**
     * Called when a list-user-subscribed event is received.
     */
    suspend fun onListUserSubscribed(event: Stream.ListEvent) {}
    /**
     * Called when a list-user-unsubscribed event is received.
     */
    suspend fun onListUserUnsubscribed(event: Stream.ListEvent) {}

    /* User event */

    /**
     * Called when any user events are received.
     */
    suspend fun onAnyUserEvent(event: Stream.UserEvent) {}

    /**
     * Called when a follow event is received.
     */
    suspend fun onFollow(event: Stream.UserEvent) {}
    /**
     * Called when an unfollow event is received.
     */
    suspend fun onUnfollow(event: Stream.UserEvent) {}
    /**
     * Called when a block event is received.
     */
    suspend fun onBlock(event: Stream.UserEvent) {}
    /**
     * Called when an unblock event is received.
     */
    suspend fun onUnblock(event: Stream.UserEvent) {}
    /**
     * Called when a mute event is received.
     */
    suspend fun onMute(event: Stream.UserEvent) {}
    /**
     * Called when an unmute event is received.
     */
    suspend fun onUnmute(event: Stream.UserEvent) {}
    /**
     * Called when a user-update event is received.
     */
    suspend fun onUserUpdate(event: Stream.UserEvent) {}

    /* Misc */

    /**
     * Called when a friends event is received.
     */
    suspend fun onFriends(friends: Stream.Friends) {}

    /**
     * Called when a delete event is received.
     */
    suspend fun onDelete(delete: Stream.Delete) {}
    /**
     * Called when a scrub-geo event is received.
     */
    suspend fun onScrubGeo(scrubGeo: Stream.ScrubGeo) {}
    /**
     * Called when a status-withheld event is received.
     */
    suspend fun onStatusWithheld(withheld: Stream.StatusWithheld) {}
    /**
     * Called when a user-withheld event is received.
     */
    suspend fun onUserWithheld(withheld: Stream.UserWithheld) {}
    /**
     * Called when a disconnect-message event is received.
     */
    suspend fun onDisconnectMessage(disconnect: Stream.Disconnect) {}
    /**
     * Called when a warning event is received.
     */
    suspend fun onWarning(warning: Stream.Warning) {}
    /**
     * Called when a limit event is received.
     */
    suspend fun onLimit(limit: Stream.Limit) {}
}

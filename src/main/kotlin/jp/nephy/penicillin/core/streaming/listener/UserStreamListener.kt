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

import jp.nephy.penicillin.models.DirectMessage
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream

interface UserStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDirectMessage(message: DirectMessage) {}

    suspend fun onAnyEvent(event: Stream.Event) {}

    /* Status event */
    suspend fun onAnyStatusEvent(event: Stream.StatusEvent) {}

    suspend fun onFavorite(event: Stream.StatusEvent) {}
    suspend fun onUnfavorite(event: Stream.StatusEvent) {}
    suspend fun onFavoritedRetweet(event: Stream.StatusEvent) {}
    suspend fun onRetweetedRetweet(event: Stream.StatusEvent) {}
    suspend fun onQuotedTweet(event: Stream.StatusEvent) {}

    /* List event */
    suspend fun onAnyListEvent(event: Stream.ListEvent) {}

    suspend fun onListCreated(event: Stream.ListEvent) {}
    suspend fun onListDestroyed(event: Stream.ListEvent) {}
    suspend fun onListUpdated(event: Stream.ListEvent) {}
    suspend fun onListMemberAdded(event: Stream.ListEvent) {}
    suspend fun onListMemberRemoved(event: Stream.ListEvent) {}
    suspend fun onListUserSubscribed(event: Stream.ListEvent) {}
    suspend fun onListUserUnsubscribed(event: Stream.ListEvent) {}

    /* User event */
    suspend fun onAnyUserEvent(event: Stream.UserEvent) {}

    suspend fun onFollow(event: Stream.UserEvent) {}
    suspend fun onUnfollow(event: Stream.UserEvent) {}
    suspend fun onBlock(event: Stream.UserEvent) {}
    suspend fun onUnblock(event: Stream.UserEvent) {}
    suspend fun onMute(event: Stream.UserEvent) {}
    suspend fun onUnmute(event: Stream.UserEvent) {}
    suspend fun onUserUpdate(event: Stream.UserEvent) {}

    /* Misc */
    suspend fun onFriends(friends: Stream.Friends) {}

    suspend fun onDelete(delete: Stream.Delete) {}
    suspend fun onScrubGeo(scrubGeo: Stream.ScrubGeo) {}
    suspend fun onStatusWithheld(withheld: Stream.StatusWithheld) {}
    suspend fun onUserWithheld(withheld: Stream.UserWithheld) {}
    suspend fun onDisconnectMessage(disconnect: Stream.Disconnect) {}
    suspend fun onWarning(warning: Stream.Warning) {}
    suspend fun onLimit(limit: Stream.Limit) {}
}

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

@file:Suppress("UNUSED")

package blue.starry.penicillin.core.streaming.handler

import blue.starry.penicillin.models.Stream

/**
 * Represents UserStream event payload JSON type.
 */
enum class UserStreamEventType {
    /**
     * [Stream.StatusEvent].
     */
    Status,

    /**
     * [Stream.ListEvent].
     */
    List,

    /**
     * [Stream.UserEvent].
     */
    User
}

/**
 * Represents UserStream event type.
 */
enum class UserStreamEvent(
    /**
     * Event payload JSON type.
     */
    val type: UserStreamEventType,

    /**
     * Event key.
     */
    val key: String
) {
    /**
     * The event fired when a status is favorited.
     */
    Favorite(UserStreamEventType.Status, "favorite"),
    /**
     * The event fired when a status is unfavorited.
     */
    Unfavorite(UserStreamEventType.Status, "unfavorite"),
    /**
     * The event fired when a favorited status is retweeted.
     */
    FavoritedRetweet(UserStreamEventType.Status, "favorited_retweet"),
    /**
     * The event fired when a retweeted status is retweeted.
     */
    RetweetedRetweet(UserStreamEventType.Status, "retweeted_retweet"),
    /**
     * The event fired when a status is quoted.
     */
    QuotedTweet(UserStreamEventType.Status, "quoted_tweet"),

    /**
     * The event fired when a list is created.
     */
    ListCreated(UserStreamEventType.List, "list_created"),
    /**
     * The event fired when a list is destroyed.
     */
    ListDestroyed(UserStreamEventType.List, "list_destroyed"),
    /**
     * The event fired when a list is updated.
     */
    ListUpdated(UserStreamEventType.List, "list_updated"),
    /**
     * The event fired when a list member is added.
     */
    ListMemberAdded(UserStreamEventType.List, "list_member_added"),
    /**
     * The event fired when a list member is removed.
     */
    ListMemberRemoved(UserStreamEventType.List, "list_member_removed"),
    /**
     * The event fired when a list is subscribed.
     */
    ListUserSubscribed(UserStreamEventType.List, "list_user_subscribed"),
    /**
     * The event fired when a list is unsubscribed.
     */
    ListUserUnsubscribed(UserStreamEventType.List, "list_user_unsubscribed"),

    /**
     * The event fired when a user is followed.
     */
    Follow(UserStreamEventType.User, "follow"),
    /**
     * The event fired when a user is unfollowed.
     */
    Unfollow(UserStreamEventType.User, "unfollow"),
    /**
     * The event fired when a user is blocked.
     */
    Block(UserStreamEventType.User, "block"),
    /**
     * The event fired when a user is unblocked.
     */
    Unblock(UserStreamEventType.User, "unblock"),
    /**
     * The event fired when a user is muted.
     */
    Mute(UserStreamEventType.User, "mute"),
    /**
     * The event fired when a user is unmuted.
     */
    Unmute(UserStreamEventType.User, "unmute"),
    /**
     * The event fired when a user is updated.
     */
    UserUpdate(UserStreamEventType.User, "user_update");
    
    companion object {
        /**
         * Finds event type by key, or null.
         */
        fun byKey(key: String): UserStreamEvent? {
            return values().find { it.key == key }
        }
    }
}

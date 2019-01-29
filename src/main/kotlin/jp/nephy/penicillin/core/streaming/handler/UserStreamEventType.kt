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

package jp.nephy.penicillin.core.streaming.handler

enum class UserStreamEventType {
    Status, List, User
}

enum class UserStreamEvent(val type: UserStreamEventType, val key: String) {
    Favorite(UserStreamEventType.Status, "favorite"),
    Unfavorite(UserStreamEventType.Status, "unfavorite"),
    FavoritedRetweet(UserStreamEventType.Status, "favorited_retweet"),
    RetweetedRetweet(UserStreamEventType.Status, "retweeted_retweet"),
    QuotedTweet(UserStreamEventType.Status, "quoted_tweet"),

    ListCreated(UserStreamEventType.List, "list_created"),
    ListDestroyed(UserStreamEventType.List, "list_destroyed"),
    ListUpdated(UserStreamEventType.List, "list_updated"),
    ListMemberAdded(UserStreamEventType.List, "list_member_added"),
    ListMemberRemoved(UserStreamEventType.List, "list_member_removed"),
    ListUserSubscribed(UserStreamEventType.List, "list_user_subscribed"),
    ListUserUnsubscribed(UserStreamEventType.List, "list_user_unsubscribed"),
    
    Follow(UserStreamEventType.User, "follow"),
    Unfollow(UserStreamEventType.User, "unfollow"),
    Block(UserStreamEventType.User, "block"),
    Unblock(UserStreamEventType.User, "unblock"),
    Mute(UserStreamEventType.User, "mute"),
    Unmute(UserStreamEventType.User, "unmute"),
    UserUpdate(UserStreamEventType.User, "user_update");
    
    companion object {
        fun byKey(key: String): UserStreamEvent? {
            return values().find { it.key == key }
        }
    }
}

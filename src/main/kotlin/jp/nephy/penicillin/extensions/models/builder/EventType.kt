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

package jp.nephy.penicillin.extensions.models.builder

interface EventType {
    val key: String
}

enum class StatusEventType(override val key: String): EventType {
    Favorite("favorite"), Unfavorite("unfavorite"),
    FavoritedRetweet("favorited_retweet"), RetweetedRetweet("retweeted_retweet"),
    QuotedTweet("quoted_tweet")
}

enum class ListEventType(override val key: String): EventType {
    ListCreated("list_created"), ListDestroyed("list_destroyed"), ListUpdated("list_updated"),
    ListMemberAdded("list_member_added"), ListMemberRemoved("list_member_removed"),
    ListUserSubscribed("list_user_subscribed"), ListUserUnsubscribed("list_user_unsubscribed")
}

enum class UserEventType(override val key: String): EventType {
    Follow("follow"), Unfollow("unfollow"),
    Block("block"), Unblock("unblock"),
    Mute("mute"), Unmute("unmute"),
    UserUpdate("user_update")
}

fun String.toEventType(): EventType? {
    val statusEvent = StatusEventType.values().find { it.key == this }
    if (statusEvent != null) {
        return statusEvent
    }
    
    val listEvent = ListEventType.values().find { it.key == this }
    if (listEvent != null) {
        return listEvent
    }
    
    return UserEventType.values().find { it.key == this }
}

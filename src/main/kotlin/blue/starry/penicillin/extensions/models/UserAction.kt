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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.extensions.models

import blue.starry.penicillin.endpoints.*
import blue.starry.penicillin.endpoints.blocks.createByUserId
import blue.starry.penicillin.endpoints.blocks.destroyByUserId
import blue.starry.penicillin.endpoints.followers.listIdsByUserId
import blue.starry.penicillin.endpoints.followers.listUsersByUserId
import blue.starry.penicillin.endpoints.friends.listIdsByUserId
import blue.starry.penicillin.endpoints.friends.listUsersByUserId
import blue.starry.penicillin.endpoints.friendships.createByUserId
import blue.starry.penicillin.endpoints.friendships.destroyByUserId
import blue.starry.penicillin.endpoints.lists.addMember
import blue.starry.penicillin.endpoints.lists.removeMember
import blue.starry.penicillin.endpoints.mutes.createByUserId
import blue.starry.penicillin.endpoints.mutes.destroyByUserId
import blue.starry.penicillin.endpoints.timeline.userTimelineByUserId
import blue.starry.penicillin.endpoints.users.reportSpamByUserId
import blue.starry.penicillin.endpoints.users.showByUserId
import blue.starry.penicillin.models.CommonUser

/**
 * Creates an action to retrieve this user.
 */
fun CommonUser.refresh() = client.users.showByUserId(userId = id)

/**
 * Creates an action to follow this user.
 */
fun CommonUser.follow() = client.friendships.createByUserId(userId = id)

/**
 * Creates an action to unfollow this user.
 */
fun CommonUser.unfollow() = client.friendships.destroyByUserId(userId = id)

/**
 * Creates an action to mute this user.
 */
fun CommonUser.mute() = client.mutes.createByUserId(userId = id)

/**
 * Creates an action to unmute this user.
 */
fun CommonUser.unmute() = client.mutes.destroyByUserId(userId = id)

/**
 * Creates an action to block this user.
 */
fun CommonUser.block() = client.blocks.createByUserId(userId = id)

/**
 * Creates an action to unblock this user.
 */
fun CommonUser.unblock() = client.blocks.destroyByUserId(userId = id)

/**
 * Creates an action to report this user as spam.
 */
fun CommonUser.reportAsSpam() = client.users.reportSpamByUserId(userId = id)

/**
 * Creates an ation to retrieve the user timeline of this user.
 */
fun CommonUser.timeline() = client.timeline.userTimelineByUserId(userId = id)

/**
 * Creates an action to retrieve friends' (follows') ids of this user.
 */
fun CommonUser.friendIds() = client.friends.listIdsByUserId(userId = id)

/**
 * Creates an action to retrieve friends' (follows') users of this user.
 */
fun CommonUser.friendUsers() = client.friends.listUsersByUserId(userId = id)

/**
 * Creates an action to retrieve followers' ids of this user.
 */
fun CommonUser.followerIds() = client.followers.listIdsByUserId(userId = id)

/**
 * Creates an action to retrieve followers' users of this user.
 */
fun CommonUser.followerUsers() = client.followers.listUsersByUserId(userId = id)

/**
 * Creates an action to add this user to the list.
 *
 * @param listId The list id.
 */
fun CommonUser.addToList(listId: Long) = client.lists.addMember(listId, userId = id)
/**
 * Creates an action to add this user to the list.
 *
 * @param slug The list slug.
 * @param ownerScreenName The owner screen name of the list.
 */
fun CommonUser.addToList(slug: String, ownerScreenName: String) = client.lists.addMember(slug, ownerScreenName, userId = id)
/**
 * Creates an action to add this user to the list.
 *
 * @param slug The list slug.
 * @param ownerId The owner id of the list.
 */
fun CommonUser.addToList(slug: String, ownerId: Long) = client.lists.addMember(slug, ownerId, userId = id)

/**
 * Creates an action to remove this user from the list.
 *
 * @param listId The list id.
 */
fun CommonUser.removeFromList(listId: Long) = client.lists.removeMember(listId, userId = id)
/**
 * Creates an action to remove this user from the list.
 *
 * @param slug The list slug.
 * @param ownerScreenName The owner screen name of the list.
 */
fun CommonUser.removeFromList(slug: String, ownerScreenName: String) = client.lists.removeMember(slug, ownerScreenName, userId = id)
/**
 * Creates an action to remove this user from the list.
 *
 * @param slug The list slug.
 * @param ownerId The owner id of the list.
 */
fun CommonUser.removeFromList(slug: String, ownerId: Long) = client.lists.removeMember(slug, ownerId, userId = id)

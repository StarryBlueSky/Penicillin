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

package jp.nephy.penicillin.extensions.models

import jp.nephy.penicillin.endpoints.*
import jp.nephy.penicillin.endpoints.blocks.createByUserId
import jp.nephy.penicillin.endpoints.blocks.destroyByUserId
import jp.nephy.penicillin.endpoints.followers.listIdsByUserId
import jp.nephy.penicillin.endpoints.followers.listUsersByUserId
import jp.nephy.penicillin.endpoints.friends.listIdsByUserId
import jp.nephy.penicillin.endpoints.friends.listUsersByUserId
import jp.nephy.penicillin.endpoints.friendships.createByUserId
import jp.nephy.penicillin.endpoints.friendships.destroyByUserId
import jp.nephy.penicillin.endpoints.lists.addMember
import jp.nephy.penicillin.endpoints.lists.removeMember
import jp.nephy.penicillin.endpoints.mutes.createByUserId
import jp.nephy.penicillin.endpoints.mutes.destroyByUserId
import jp.nephy.penicillin.endpoints.timeline.userTimelineByUserId
import jp.nephy.penicillin.endpoints.users.reportSpamByUserId
import jp.nephy.penicillin.endpoints.users.showByUserId
import jp.nephy.penicillin.models.CommonUser

fun CommonUser.refresh() = client.users.showByUserId(userId = id)

fun CommonUser.follow() = client.friendships.createByUserId(userId = id)
fun CommonUser.unfollow() = client.friendships.destroyByUserId(userId = id)

fun CommonUser.mute() = client.mutes.createByUserId(userId = id)
fun CommonUser.unmute() = client.mutes.destroyByUserId(userId = id)

fun CommonUser.block() = client.blocks.createByUserId(userId = id)
fun CommonUser.unblock() = client.blocks.destroyByUserId(userId = id)

fun CommonUser.reportAsSpam() = client.users.reportSpamByUserId(userId = id)

fun CommonUser.timeline() = client.timeline.userTimelineByUserId(userId = id)

fun CommonUser.friendIds() = client.friends.listIdsByUserId(userId = id)
fun CommonUser.friendUsers() = client.friends.listUsersByUserId(userId = id)

fun CommonUser.followerIds() = client.followers.listIdsByUserId(userId = id)
fun CommonUser.followerUsers() = client.followers.listUsersByUserId(userId = id)

fun CommonUser.addToList(listId: Long) = client.lists.addMember(listId, userId = id)
fun CommonUser.addToList(slug: String, ownerScreenName: String) = client.lists.addMember(slug, ownerScreenName, userId = id)
fun CommonUser.addToList(slug: String, ownerId: Long) = client.lists.addMember(slug, ownerId, userId = id)

fun CommonUser.removeFromList(listId: Long) = client.lists.removeMember(listId, userId = id)
fun CommonUser.removeFromList(slug: String, ownerScreenName: String) = client.lists.removeMember(slug, ownerScreenName, userId = id)
fun CommonUser.removeFromList(slug: String, ownerId: Long) = client.lists.removeMember(slug, ownerId, userId = id)


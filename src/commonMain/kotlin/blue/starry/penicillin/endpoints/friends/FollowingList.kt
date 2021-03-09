/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.endpoints.friends

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.request.action.CursorJsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Friends
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.User
import blue.starry.penicillin.models.cursor.CursorUsers

/**
 * Returns users who follows specific user.
 *
 * @param userId The ID of the user for whom to return results.
 * @param count The number of users to return per page, up to a maximum of 200.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friends] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 * @see followingListByScreenName
 */
public fun Friends.followingListByUserId(
    userId: Long,
    count: Int? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = followingList(userId, null, count, *options)

/**
 * Returns users who follows specific user.
 *
 * @param screenName The screen name of the user for whom to return results.
 * @param count The number of users to return per page, up to a maximum of 200.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friends] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 * @see followingListByUserId
 */
public fun Friends.followingListByScreenName(
    screenName: String,
    count: Int? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = followingList(null, screenName, count, *options)

private fun Friends.followingList(
    userId: Long? = null,
    screenName: String? = null,
    count: Int? = null,
    vararg options: Option
) = client.session.get("/1.1/friends/following/list.json") {
    emulationModes += EmulationMode.TwitterForiPhone

    parameters(
        "user_id" to userId,
        "screen_name" to screenName,
        "count" to count,
        *options
    )
}.cursorJsonObject { CursorUsers(it, client) }

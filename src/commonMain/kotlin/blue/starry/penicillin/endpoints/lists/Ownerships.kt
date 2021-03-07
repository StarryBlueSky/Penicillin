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

package blue.starry.penicillin.endpoints.lists

import blue.starry.penicillin.core.request.action.CursorJsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Lists
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.TwitterList
import blue.starry.penicillin.models.cursor.CursorLists

/**
 * Returns the lists owned by the specified Twitter user. Private lists will only be shown if the authenticated user is also the owner of the lists.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-ownerships)
 *
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
public fun Lists.ownerships(
    count: Int? = null,
    cursor: Long? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorLists, TwitterList> = ownershipsInternal(null, null, count, cursor, *options)

/**
 * Returns the lists owned by the specified Twitter user. Private lists will only be shown if the authenticated user is also the owner of the lists.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-ownerships)
 *
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
public fun Lists.ownershipsByUserId(
    userId: Long,
    count: Int? = null,
    cursor: Long? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorLists, TwitterList> = ownershipsInternal(userId, null, count, cursor, *options)

/**
 * Returns the lists owned by the specified Twitter user. Private lists will only be shown if the authenticated user is also the owner of the lists.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-ownerships)
 *
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
public fun Lists.ownerships(
    screenName: String,
    count: Int? = null,
    cursor: Long? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorLists, TwitterList> = ownershipsInternal(null, screenName, count, cursor, *options)

private fun Lists.ownershipsInternal(
    userId: Long? = null,
    screenName: String? = null,
    count: Int? = null,
    cursor: Long? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/ownerships.json") {
    parameters(
        "user_id" to userId,
        "screen_name" to screenName,
        "count" to count,
        "cursor" to cursor,
        *options
    )
}.cursorJsonObject { CursorLists(it, client) }

/**
 * Shorthand property to [Lists.ownerships].
 * @see Lists.ownerships
 */
public val Lists.ownerships: CursorJsonObjectApiAction<CursorLists, TwitterList>
    get() = ownerships()

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

package jp.nephy.penicillin.endpoints.lists

import jp.nephy.penicillin.core.request.action.CursorJsonObjectApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Lists
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.cursor.CursorLists

/**
 * Returns the lists the specified user has been added to. If user_id or screen_name are not provided, the memberships for the authenticating user are returned.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-memberships)
 *
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param filterToOwnedLists When set to true , t or 1 , will return just lists the authenticating user owns, and the user represented by user_id or screen_name is a member of.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
fun Lists.memberships(
    count: Int? = null,
    cursor: Long? = null,
    filterToOwnedLists: Boolean? = null,
    vararg options: Option
) = membershipsInternal(null, null, count, cursor, filterToOwnedLists, *options)

/**
 * Returns the lists the specified user has been added to. If user_id or screen_name are not provided, the memberships for the authenticating user are returned.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-memberships)
 *
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param filterToOwnedLists When set to true , t or 1 , will return just lists the authenticating user owns, and the user represented by user_id or screen_name is a member of.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
fun Lists.membershipsByUserId(
    userId: Long,
    count: Int? = null,
    cursor: Long? = null,
    filterToOwnedLists: Boolean? = null,
    vararg options: Option
) = membershipsInternal(userId, null, count, cursor, filterToOwnedLists, *options)

/**
 * Returns the lists the specified user has been added to. If user_id or screen_name are not provided, the memberships for the authenticating user are returned.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-memberships)
 *
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param count The amount of results to return per page. Defaults to 20. No more than 1000 results will ever be returned in a single page.
 * @param cursor Breaks the results into pages. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. It is recommended to always use cursors when the method supports them. See [Cursoring](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param filterToOwnedLists When set to true , t or 1 , will return just lists the authenticating user owns, and the user represented by user_id or screen_name is a member of.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorLists] model.
 */
fun Lists.membershipsByScreenName(
    screenName: String,
    count: Int? = null,
    cursor: Long? = null,
    filterToOwnedLists: Boolean? = null,
    vararg options: Option
) = membershipsInternal(null, screenName, count, cursor, filterToOwnedLists, *options)

private fun Lists.membershipsInternal(
    userId: Long? = null,
    screenName: String? = null,
    count: Int? = null,
    cursor: Long? = null,
    filterToOwnedLists: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/memberships.json") {
    parameters(
        "user_id" to userId,
        "screen_name" to screenName,
        "count" to count,
        "cursor" to cursor,
        "filter_to_owned_lists" to filterToOwnedLists,
        *options
    )
}.cursorJsonObject<CursorLists>()

/**
 * Shorthand property to [Lists.memberships].
 * @see Lists.memberships
 */
val Lists.memberships
    get() = memberships()

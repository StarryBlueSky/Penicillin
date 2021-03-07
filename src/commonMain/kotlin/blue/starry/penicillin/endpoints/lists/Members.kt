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
import blue.starry.penicillin.models.User
import blue.starry.penicillin.models.cursor.CursorUsers

/**
 * Returns the members of the specified list. Private list members will only be shown if the authenticated user owns the specified list.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-members)
 * 
 * @param listId The numerical id of the list.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Causes the collection of list members to be broken into "pages" of consistent sizes (specified by the count parameter). If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See Using cursors to navigate collections for more information.
 * @param includeEntities The entities node will not be included when set to false.
 * @param skipStatus When set to either true, t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.members(
    listId: Long,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = members(listId, null, null, null, count, cursor, includeEntities, skipStatus, *options)

/**
 * Returns the members of the specified list. Private list members will only be shown if the authenticated user owns the specified list.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-members)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Causes the collection of list members to be broken into "pages" of consistent sizes (specified by the count parameter). If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See Using cursors to navigate collections for more information.
 * @param includeEntities The entities node will not be included when set to false.
 * @param skipStatus When set to either true, t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.membersByOwnerScreenName(
    slug: String,
    ownerScreenName: String,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = members(null, slug, ownerScreenName, null, count, cursor, includeEntities, skipStatus, *options)

/**
 * Returns the members of the specified list. Private list members will only be shown if the authenticated user owns the specified list.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-members)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Causes the collection of list members to be broken into "pages" of consistent sizes (specified by the count parameter). If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See Using cursors to navigate collections for more information.
 * @param includeEntities The entities node will not be included when set to false.
 * @param skipStatus When set to either true, t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.membersByOwnerId(
    slug: String,
    ownerId: Long,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = members(null, slug, null, ownerId, count, cursor, includeEntities, skipStatus, *options)

private fun Lists.members(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/members.json") {
    parameters(
        "list_id" to listId,
        "slug" to slug,
        "owner_screen_name" to ownerScreenName,
        "owner_id" to ownerId,
        "count" to count,
        "cursor" to cursor,
        "include_entities" to includeEntities,
        "skip_status" to skipStatus,
        *options
    )
}.cursorJsonObject { CursorUsers(it, client) }

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
 * Returns the subscribers of the specified list. Private list subscribers will only be shown if the authenticated user owns the specified list.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-subscribers)
 * 
 * @param listId The numerical id of the list.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Breaks the results into pages. A single page contains 20 lists. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param includeEntities When set to either true , t or 1 , each tweet will include a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. While entities are opt-in on timelines at present, they will be made a default component of output in the future. See [Tweet Entities](https://developer.twitter.com/overview/api/tweets) for more details.
 * @param skipStatus When set to either true , t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.subscribers(
    listId: Long,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = subscribers(listId, null, null, null, count, cursor, includeEntities, skipStatus, *options)

/**
 * Returns the subscribers of the specified list. Private list subscribers will only be shown if the authenticated user owns the specified list.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-subscribers)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Breaks the results into pages. A single page contains 20 lists. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param includeEntities When set to either true , t or 1 , each tweet will include a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. While entities are opt-in on timelines at present, they will be made a default component of output in the future. See [Tweet Entities](https://developer.twitter.com/overview/api/tweets) for more details.
 * @param skipStatus When set to either true , t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.subscribersByOwnerScreenName(
    slug: String,
    ownerScreenName: String,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = subscribers(null, slug, ownerScreenName, null, count, cursor, includeEntities, skipStatus, *options)

/**
 * Returns the subscribers of the specified list. Private list subscribers will only be shown if the authenticated user owns the specified list.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-subscribers)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param count Specifies the number of results to return per page (see cursor below). The default is 20, with a maximum of 5,000.
 * @param cursor Breaks the results into pages. A single page contains 20 lists. Provide a value of -1 to begin paging. Provide values as returned in the response body's next_cursor and previous_cursor attributes to page back and forth in the list. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param includeEntities When set to either true , t or 1 , each tweet will include a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. While entities are opt-in on timelines at present, they will be made a default component of output in the future. See [Tweet Entities](https://developer.twitter.com/overview/api/tweets) for more details.
 * @param skipStatus When set to either true , t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorUsers] model.
 */
public fun Lists.subscribersByOwnerId(
    slug: String,
    ownerId: Long,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorUsers, User> = subscribers(null, slug, null, ownerId, count, cursor, includeEntities, skipStatus, *options)

private fun Lists.subscribers(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    count: Int? = null,
    cursor: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/subscribers.json") {
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

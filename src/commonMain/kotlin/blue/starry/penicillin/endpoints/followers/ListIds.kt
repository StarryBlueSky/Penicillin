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

package blue.starry.penicillin.endpoints.followers

import blue.starry.penicillin.core.request.action.CursorJsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Followers
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.cursor.CursorIds

/**
 * Returns a cursored collection of user IDs for every user following the specified user.
 * At this time, results are ordered with the most recent following first — however, this ordering is subject to unannounced change and eventual consistency issues. Results are given in groups of 5,000 user IDs and multiple "pages" of results can be navigated through using the next_cursor value in subsequent requests. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * This method is especially powerful when used in conjunction with [GET users/lookup](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup), a method that allows you to convert user IDs into full [user objects](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) in bulk.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-followers-ids)
 *
 * @param cursor Causes the list of connections to be broken into pages of no more than 5000 IDs at a time. The number of IDs returned is not guaranteed to be 5000 as suspended users are filtered out after connections are queried. If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param stringifyIds Some programming environments will not consume Twitter IDs due to their size. Provide this option to have IDs returned as strings instead. More about [Twitter IDs](https://developer.twitter.com/en/docs/basics/twitter-ids).
 * @param count Specifies the number of IDs attempt retrieval of, up to a maximum of 5,000 per distinct request. The value of count is best thought of as a limit to the number of results to return. When using the count parameter with this method, it is wise to use a consistent count value across all requests to the same user's collection. Usage of this parameter is encouraged in environments where all 5,000 IDs constitutes too large of a response.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Followers] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorIds] model.
 */
public fun Followers.listIds(
    cursor: Long? = null,
    stringifyIds: Boolean? = null,
    count: Int? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorIds, Long> = listIdsInternal(null, null, cursor, stringifyIds, count, *options)

/**
 * Returns a cursored collection of user IDs for every user following the specified user.
 * At this time, results are ordered with the most recent following first — however, this ordering is subject to unannounced change and eventual consistency issues. Results are given in groups of 5,000 user IDs and multiple "pages" of results can be navigated through using the next_cursor value in subsequent requests. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * This method is especially powerful when used in conjunction with [GET users/lookup](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup), a method that allows you to convert user IDs into full [user objects](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) in bulk.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-followers-ids)
 *
 * @param userId The ID of the user for whom to return results.
 * @param cursor Causes the list of connections to be broken into pages of no more than 5000 IDs at a time. The number of IDs returned is not guaranteed to be 5000 as suspended users are filtered out after connections are queried. If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param stringifyIds Some programming environments will not consume Twitter IDs due to their size. Provide this option to have IDs returned as strings instead. More about [Twitter IDs](https://developer.twitter.com/en/docs/basics/twitter-ids).
 * @param count Specifies the number of IDs attempt retrieval of, up to a maximum of 5,000 per distinct request. The value of count is best thought of as a limit to the number of results to return. When using the count parameter with this method, it is wise to use a consistent count value across all requests to the same user's collection. Usage of this parameter is encouraged in environments where all 5,000 IDs constitutes too large of a response.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Followers] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorIds] model.
 * @see listIdsByScreenName
 * @see listIds
 */
public fun Followers.listIdsByUserId(
    userId: Long,
    cursor: Long? = null,
    stringifyIds: Boolean? = null,
    count: Int? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorIds, Long> = listIdsInternal(userId, null, cursor, stringifyIds, count, *options)

/**
 * Returns a cursored collection of user IDs for every user following the specified user.
 * At this time, results are ordered with the most recent following first — however, this ordering is subject to unannounced change and eventual consistency issues. Results are given in groups of 5,000 user IDs and multiple "pages" of results can be navigated through using the next_cursor value in subsequent requests. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * This method is especially powerful when used in conjunction with [GET users/lookup](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup), a method that allows you to convert user IDs into full [user objects](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) in bulk.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-followers-ids)
 *
 * @param screenName The screen name of the user for whom to return results.
 * @param cursor Causes the list of connections to be broken into pages of no more than 5000 IDs at a time. The number of IDs returned is not guaranteed to be 5000 as suspended users are filtered out after connections are queried. If no cursor is provided, a value of -1 will be assumed, which is the first "page." The response from the API will include a previous_cursor and next_cursor to allow paging back and forth. See [Using cursors to navigate collections](https://developer.twitter.com/en/docs/basics/cursoring) for more information.
 * @param stringifyIds Some programming environments will not consume Twitter IDs due to their size. Provide this option to have IDs returned as strings instead. More about [Twitter IDs](https://developer.twitter.com/en/docs/basics/twitter-ids).
 * @param count Specifies the number of IDs attempt retrieval of, up to a maximum of 5,000 per distinct request. The value of count is best thought of as a limit to the number of results to return. When using the count parameter with this method, it is wise to use a consistent count value across all requests to the same user's collection. Usage of this parameter is encouraged in environments where all 5,000 IDs constitutes too large of a response.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Followers] endpoint instance.
 * @return [CursorJsonObjectApiAction] for [CursorIds] model.
 * @see listIdsByUserId
 * @see listIds
 */
public fun Followers.listIdsByScreenName(
    screenName: String,
    cursor: Long? = null,
    stringifyIds: Boolean? = null,
    count: Int? = null,
    vararg options: Option
): CursorJsonObjectApiAction<CursorIds, Long> = listIdsInternal(null, screenName, cursor, stringifyIds, count, *options)

private fun Followers.listIdsInternal(
    userId: Long? = null,
    screenName: String? = null,
    cursor: Long? = null,
    stringifyIds: Boolean? = null,
    count: Int? = null,
    vararg options: Option
) = client.session.get("/1.1/followers/ids.json") {
    parameters(
        "user_id" to userId,
        "screen_name" to screenName,
        "cursor" to cursor,
        "stringify_ids" to stringifyIds,
        "count" to count,
        *options
    )
}.cursorJsonObject { CursorIds(it, client) }

/**
 * Shorthand property to [Followers.listIds].
 * @see Followers.listIds
 */
public val Followers.listIds: CursorJsonObjectApiAction<CursorIds, Long>
    get() = listIdsInternal()

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

package jp.nephy.penicillin.endpoints.collections

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Collections
import jp.nephy.penicillin.models.Collection

/**
 * Find Collections created by a specific user or containing a specific curated Tweet.
 * Results are organized in a cursored collection.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/get-collections-list)
 *
 * @param tweetId The identifier of the Tweet for which to return results.
 * @param count Specifies the maximum number of results to include in the response. Specify a count between 1 and 200. A next_cursor value will be provided in the response if additional results are available.
 * @param cursor A string identifying the segment of the current result set to retrieve. Values for this parameter are yielded in the cursors node attached to response objects. Usage of the count parameter affects cursoring.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Collections] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.List] model.
 */
fun Collections.list(
    tweetId: Long? = null,
    count: Int? = null,
    cursor: String? = null,
    vararg options: Option
) = listInternal(null, null, tweetId, count, cursor, *options)

/**
 * Find Collections created by a specific user or containing a specific curated Tweet.
 * Results are organized in a cursored collection.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/get-collections-list)
 *
 * @param userId The ID of the user for whom to return results.
 * @param tweetId The identifier of the Tweet for which to return results.
 * @param count Specifies the maximum number of results to include in the response. Specify a count between 1 and 200. A next_cursor value will be provided in the response if additional results are available.
 * @param cursor A string identifying the segment of the current result set to retrieve. Values for this parameter are yielded in the cursors node attached to response objects. Usage of the count parameter affects cursoring.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Collections] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.List] model.
 */
fun Collections.listByUserId(
    userId: Long,
    tweetId: Long? = null,
    count: Int? = null,
    cursor: String? = null,
    vararg options: Option
) = listInternal(userId, null, tweetId, count, cursor, *options)

/**
 * Find Collections created by a specific user or containing a specific curated Tweet.
 * Results are organized in a cursored collection.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/get-collections-list)
 *
 * @param screenName The screen name of the user for whom to return results.
 * @param tweetId The identifier of the Tweet for which to return results.
 * @param count Specifies the maximum number of results to include in the response. Specify a count between 1 and 200. A next_cursor value will be provided in the response if additional results are available.
 * @param cursor A string identifying the segment of the current result set to retrieve. Values for this parameter are yielded in the cursors node attached to response objects. Usage of the count parameter affects cursoring.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Collections] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.List] model.
 */
fun Collections.listByScreenName(
    screenName: String,
    tweetId: Long? = null,
    count: Int? = null,
    cursor: String? = null,
    vararg options: Option
) = listInternal(null, screenName, tweetId, count, cursor, *options)

private fun Collections.listInternal(
    userId: Long? = null,
    screenName: String? = null,
    tweetId: Long? = null,
    count: Int? = null,
    cursor: String? = null,
    vararg options: Option
) = client.session.get("/1.1/collections/list.json") {
    parameter(
        "user_id" to userId,
        "screen_name" to screenName,
        "tweet_id" to tweetId,
        "count" to count,
        "cursor" to cursor,
        *options
    )
}.jsonObject<Collection.List>()

 /**
 * Shorthand property to [Collections.list].
 * @see Collections.list
 */
val Collections.list
    get() = list()

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

package blue.starry.penicillin.endpoints.friendships

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Friendships
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Friendships.Show

/**
 * Returns detailed information about the relationship between two arbitrary users.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-friendships-show)
 * 
 * @param sourceId The user_id of the subject user.
 * @param targetId The user_id of the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [Show] model.
 */
public fun Friendships.showByUserId(
    sourceId: Long,
    targetId: Long,
    vararg options: Option
): JsonObjectApiAction<Show> = show(sourceId, null, targetId, null, *options)

/**
 * Returns detailed information about the relationship between two arbitrary users.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-friendships-show)
 *
 * @param targetId The user_id of the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [Show] model.
 */
public fun Friendships.showByUserId(
    targetId: Long,
    vararg options: Option
): JsonObjectApiAction<Show> = show(null, null, targetId, null, *options)

/**
 * Returns detailed information about the relationship between two arbitrary users.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-friendships-show)
 *
 * @param sourceScreenName The screen_name of the subject user.
 * @param targetScreenName The screen_name of the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [Show] model.
 */
public fun Friendships.showByScreenName(
    sourceScreenName: String,
    targetScreenName: String,
    vararg options: Option
): JsonObjectApiAction<Show> = show(null, sourceScreenName, null, targetScreenName, *options)

/**
 * Returns detailed information about the relationship between two arbitrary users.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-friendships-show)
 *
 * @param targetScreenName The screen_name of the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [Show] model.
 */
public fun Friendships.showByScreenName(
    targetScreenName: String,
    vararg options: Option
): JsonObjectApiAction<Show> = show(null, null, null, targetScreenName, *options)

private fun Friendships.show(
    sourceId: Long? = null,
    sourceScreenName: String? = null,
    targetId: Long? = null,
    targetScreenName: String? = null,
    vararg options: Option
) = client.session.get("/1.1/friendships/show.json") {
    parameters(
        "source_id" to sourceId,
        "source_screen_name" to sourceScreenName,
        "target_id" to targetId,
        "target_screen_name" to targetScreenName,
        *options
    )
}.jsonObject { Show(it, client) }

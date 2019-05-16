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

package jp.nephy.penicillin.endpoints.friendships


import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.formBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Friendships
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.User

/**
 * Allows the authenticating user to follow (friend) the user specified in the ID parameter.
 * Returns the followed user when successful. Returns a string describing the failure condition when unsuccessful. If the user is already friends with the user a HTTP 403 may be returned, though for performance reasons this method may also return a HTTP 200 OK message even if the follow relationship already exists.
 * Actions taken in this method are asynchronous. Changes will be eventually consistent.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/post-friendships-create)
 * 
 * @param userId The ID of the user to follow.
 * @param follow Enable notifications for the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 * @see createByScreenName
 */
fun Friendships.createByUserId(
    userId: Long,
    follow: Boolean? = null,
    vararg options: Option
) = create(userId, null, follow, *options)

/**
 * Allows the authenticating user to follow (friend) the user specified in the ID parameter.
 * Returns the followed user when successful. Returns a string describing the failure condition when unsuccessful. If the user is already friends with the user a HTTP 403 may be returned, though for performance reasons this method may also return a HTTP 200 OK message even if the follow relationship already exists.
 * Actions taken in this method are asynchronous. Changes will be eventually consistent.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/post-friendships-create)
 *
 * @param screenName The screen name of the user to follow.
 * @param follow Enable notifications for the target user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 * @see createByUserId
 */
fun Friendships.createByScreenName(
    screenName: String,
    follow: Boolean? = null,
    vararg options: Option
) = create(null, screenName, follow, *options)

private fun Friendships.create(
    userId: Long? = null,
    screenName: String? = null,
    follow: Boolean? = null,
    vararg options: Option
) = client.session.post("/1.1/friendships/create.json") {
    formBody(
        "user_id" to userId,
        "screen_name" to screenName,
        "follow" to follow,
        *options
    )
}.jsonObject<User>()

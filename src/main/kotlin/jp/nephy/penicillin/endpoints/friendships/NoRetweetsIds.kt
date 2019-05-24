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


import jp.nephy.jsonkt.longList
import jp.nephy.jsonkt.toJsonArray
import jp.nephy.penicillin.core.request.action.DelegatedAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Friendships
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.extensions.DelegatedAction
import jp.nephy.penicillin.extensions.await

/**
 * Returns a collection of user_ids that the currently authenticated user does not want to receive retweets from.
 * Use [POST friendships/update](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/post-friendships-update) to set the "no retweets" status for a given user account on behalf of the current user.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-friendships-no_retweets-ids)
 * 
 * @param stringifyIds Some programming environments will not consume Twitter IDs due to their size. Provide this option to have IDs returned as strings instead. Read more about [Twitter IDs](https://developer.twitter.com/en/docs/basics/twitter-ids). This parameter is important to use in Javascript environments.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Friendships] endpoint instance.
 * @return [DelegatedAction] for [List] of [Long].
 */
fun Friendships.noRetweetsIds(
    stringifyIds: Boolean? = null,
    vararg options: Option
) = DelegatedAction {
    val result = client.session.get("/1.1/friendships/no_retweets/ids.json") {
        parameters(
            "stringify_ids" to stringifyIds, *options
        )
    }.text().await()
    
    result.content.toJsonArray().longList
}

 /**
 * Shorthand property to [Friendships.noRetweetsIds].
 * @see Friendships.noRetweetsIds
 */
val Friendships.noRetweetsIds
    get() = noRetweetsIds()

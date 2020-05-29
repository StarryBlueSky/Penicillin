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

package jp.nephy.penicillin.endpoints.users

import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.request.action.JsonArrayApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Users
import jp.nephy.penicillin.endpoints.common.TweetMode
import jp.nephy.penicillin.models.User

/**
 * Returns fully-hydrated user objects for up to 100 users per request, as specified by comma-separated values passed to the user_id and/or screen_name parameters.
 * This method is especially useful when used in conjunction with collections of user IDs returned from GET friends / ids and GET followers / ids.
 * GET users / show is used to retrieve a single user object.
 * There are a few things to note when using this method.
 * - You must be following a protected user to be able to see their most recent status update. If you don't follow a protected user their status will be removed.
 * - The order of user IDs or screen names may not match the order of users in the returned array.
 * - If a requested user is unknown, suspended, or deleted, then that user will not be returned in the results list.
 * - If none of your lookup criteria can be satisfied by returning a user object, a HTTP 404 will be thrown.
 * - You are strongly encouraged to use a POST for larger requests.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup)
 * 
 * @param screenNames A list of screen names, up to 100 are allowed in a single request. You are strongly encouraged to use a POST for larger (up to 100 screen names) requests.
 * @param includeEntities The entities node that may appear within embedded statuses will not be included when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonArrayApiAction] for [User] model.
 */
fun Users.lookupByScreenNames(
    screenNames: List<String>,
    includeEntities: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
) = lookup(screenNames, null, includeEntities, tweetMode, *options)

/**
 * Returns fully-hydrated user objects for up to 100 users per request, as specified by comma-separated values passed to the user_id and/or screen_name parameters.
 * This method is especially useful when used in conjunction with collections of user IDs returned from GET friends / ids and GET followers / ids.
 * GET users / show is used to retrieve a single user object.
 * There are a few things to note when using this method.
 * - You must be following a protected user to be able to see their most recent status update. If you don't follow a protected user their status will be removed.
 * - The order of user IDs or screen names may not match the order of users in the returned array.
 * - If a requested user is unknown, suspended, or deleted, then that user will not be returned in the results list.
 * - If none of your lookup criteria can be satisfied by returning a user object, a HTTP 404 will be thrown.
 * - You are strongly encouraged to use a POST for larger requests.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup)
 *
 * @param userIds A list of user IDs, up to 100 are allowed in a single request. You are strongly encouraged to use a POST for larger requests.
 * @param includeEntities The entities node that may appear within embedded statuses will not be included when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonArrayApiAction] for [User] model.
 */
fun Users.lookupByIds(
    userIds: List<Long>,
    includeEntities: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
) = lookup(null, userIds, includeEntities, tweetMode, *options)

private fun Users.lookup(
    screenNames: List<String>? = null,
    userIds: List<Long>? = null,
    includeEntities: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
) = client.session.get("/1.1/users/lookup.json") {
    parameters(
        "ext" to "mediaColor",
        "include_entities" to 1,
        "include_user_entities" to true,
        "include_profile_interstitial_type" to true,
        "include_profile_location" to true,
        "include_user_symbol_entities" to true,
        "include_user_hashtag_entities" to true,
        "include_user_mention_entities" to true,
        mode = EmulationMode.TwitterForiPhone
    )
    parameters(
        "screen_name" to screenNames?.joinToString(","),
        "user_id" to userIds?.joinToString(","),
        "include_entities" to includeEntities,
        "tweet_mode" to tweetMode,
        *options
    )
}.jsonArray { User(it, client) }

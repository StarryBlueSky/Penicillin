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
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Users
import jp.nephy.penicillin.models.User

/**
 * Returns a [variety of information](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) about the user specified by the required user_id or screen_name parameter. The author's most recent Tweet will be returned inline when possible.
 * [GET users/lookup](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup) is used to retrieve a bulk collection of user objects.
 * You must be following a protected user to be able to see their most recent Tweet. If you don't follow a protected user, the user's Tweet will be removed. A Tweet will not always be returned in the current_status field.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-show)
 * 
 * @param screenName The screen name of the user for whom to return results. Either a id or screen_name is required for this method.
 * @param includeEntities The entities node will not be included when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
fun Users.showByScreenName(
    screenName: String,
    includeEntities: Boolean? = null,
    vararg options: Option
) = show(screenName, null, includeEntities, *options)

/**
 * Returns a [variety of information](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) about the user specified by the required user_id or screen_name parameter. The author's most recent Tweet will be returned inline when possible.
 * [GET users/lookup](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup) is used to retrieve a bulk collection of user objects.
 * You must be following a protected user to be able to see their most recent Tweet. If you don't follow a protected user, the user's Tweet will be removed. A Tweet will not always be returned in the current_status field.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-show)
 *
 * @param userId The ID of the user for whom to return results. Either an id or screen_name is required for this method.
 * @param includeEntities The entities node will not be included when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
fun Users.showByUserId(
    userId: Long,
    includeEntities: Boolean? = null,
    vararg options: Option
) = show(null, userId, includeEntities, *options)

private fun Users.show(
    screenName: String? = null,
    userId: Long? = null,
    includeEntities: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/users/show.json") {
    parameters(
        "ext" to "mediaColor",
        "include_entities" to 1,
        "include_user_entities" to true,
        "include_profile_interstitial_type" to true,
        "include_user_symbol_entities" to true,
        "include_user_hashtag_entities" to true,
        "include_user_mention_entities" to true,
        mode = EmulationMode.TwitterForiPhone
    )
    parameters(
        "screen_name" to screenName,
        "user_id" to userId,
        "include_entities" to includeEntities,
        *options
    )
}.jsonObject<User>()

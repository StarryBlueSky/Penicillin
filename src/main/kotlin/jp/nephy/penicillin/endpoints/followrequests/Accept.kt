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

package jp.nephy.penicillin.endpoints.followrequests

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.formBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.FollowRequests
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import jp.nephy.penicillin.models.User

/**
 * Accepts the follow request from specific user.
 * 
 * @param screenName The screen name of the user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [FollowRequests] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
@PrivateEndpoint
fun FollowRequests.acceptByScreenName(
    screenName: String,
    vararg  options: Option
) = accept(screenName, null, *options)

/**
 * Accepts the follow request from specific user.
 *
 * @param userId The numeric ID of the user.
 * @param options Optional. Custom parameters of this request.
 * @receiver [FollowRequests] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
@PrivateEndpoint
fun FollowRequests.acceptByUserId(
    userId: Long,
    vararg  options: Option
) = accept(null, userId, *options)

private fun FollowRequests.accept(
    screenName: String? = null,
    userId: Long? = null,
    vararg options: Option
) = client.session.post("/1.1/friendships/accept.json") {
    formBody(
        "ext" to "mediaColor",
        "include_entities" to "1",
        "include_profile_interstitial_type" to "true",
        "include_profile_location" to "true",
        "include_user_entities" to "true",
        "include_user_hashtag_entities" to "true",
        "include_user_mention_entities" to "true",
        "include_user_symbol_entities" to "true",
        "screen_name" to screenName,
        "user_id" to userId,
        *options
    )
}.jsonObject<User>()

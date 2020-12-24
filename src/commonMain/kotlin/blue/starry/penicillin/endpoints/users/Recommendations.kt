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

package blue.starry.penicillin.endpoints.users

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Users
import blue.starry.penicillin.models.Recommendation

/**
 * Undocumented endpoint.
 * 
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonArrayApiAction] for [Recommendation] model.
 */
public fun Users.recommendationsByScreenName(
    screenName: String,
    vararg options: Option
): JsonArrayApiAction<Recommendation> = recommendations(screenName, null, *options)

/**
 * Undocumented endpoint.
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonArrayApiAction] for [Recommendation] model.
 */
public fun Users.recommendationsByUserId(
    userId: Long,
    vararg options: Option
): JsonArrayApiAction<Recommendation> = recommendations(null, userId, *options)

private fun Users.recommendations(
    screenName: String? = null,
    userId: Long? = null,
    vararg options: Option
) = client.session.get("/1.1/users/recommendations.json") {
    emulationModes += EmulationMode.TwitterForiPhone

    parameters(
        "connections" to "true",
        "display_location" to "st-component",
        "ext" to "mediaColor",
        "include_entities" to "1",
        "include_profile_interstitial_type" to "true",
        "include_profile_location" to "true",
        "include_user_entities" to "true",
        "include_user_hashtag_entities" to "true",
        "include_user_mention_entities" to "true",
        "include_user_symbol_entities" to "true",
        "limit" to "3",
        "pc" to "true",
        "screen_name" to screenName,
        "user_id" to userId,
        *options
    )
}.jsonArray { Recommendation(it, client) }

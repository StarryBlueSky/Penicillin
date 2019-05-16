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

package jp.nephy.penicillin.endpoints.activity

import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.request.action.JsonArrayApiAction
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Activity
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import jp.nephy.penicillin.models.ActivityEvent

/**
 * Unknown endpoint.
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Activity] endpoint instance.
 * @return [JsonArrayApiAction] for [ActivityEvent] model.
 */
@PrivateEndpoint(EmulationMode.Tweetdeck)
fun Activity.byFriends(
    count: Int? = null,
    vararg options: Option
) = client.session.get("/1.1/activity/by_friends.json") {
    parameter(
        "count" to (count ?: 40),
        "skip_aggregation" to true,
        "cards_platform" to "Web-13",
        "include_entities" to 1,
        "include_user_entities" to 1,
        "include_cards" to 1,
        "send_error_codes" to 1,
        "tweet_mode" to "extended",
        "include_ext_alt_text" to true,
        "include_reply_count" to true,
        *options,
        emulationMode = EmulationMode.Tweetdeck
    )
}.jsonArray<ActivityEvent>()

/**
 * Shorthand property to [Activity.byFriends].
 * @see Activity.byFriends
 */
val Activity.byFriends
    get() = byFriends()

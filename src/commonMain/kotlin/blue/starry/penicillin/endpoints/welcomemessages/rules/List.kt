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

package blue.starry.penicillin.endpoints.welcomemessages.rules

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.WelcomeMessageRules
import blue.starry.penicillin.models.WelcomeMessageRule

/**
 * Returns a list of Welcome Message Rules.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/api-reference/list-welcome-message-rules)
 * 
 * @param count Number of welcome message rules to be returned. Max of 50. Default is 20.
 * @param cursor For paging through result sets greater than 1 page, use the “next_cursor” property from the previous request.
 * @param options Optional. Custom parameters of this request.
 * @receiver [WelcomeMessageRules] endpoint instance.
 * @return [JsonObjectApiAction] for [WelcomeMessageRule.List] model.
 */
public fun WelcomeMessageRules.list(
    count: Int? = null,
    cursor: String? = null,
    vararg options: Option
): JsonObjectApiAction<WelcomeMessageRule.List> = client.session.get("/1.1/direct_messages/welcome_messages/rules/list.json") {
    parameters(
        "count" to count,
        "cursor" to cursor,
        *options
    )
}.jsonObject { WelcomeMessageRule.List(it, client) }

/**
 * Shorthand property to [WelcomeMessageRules.list].
 * @see WelcomeMessageRules.list
 */ 
public val WelcomeMessageRules.list: JsonObjectApiAction<WelcomeMessageRule.List>
    get() = list()

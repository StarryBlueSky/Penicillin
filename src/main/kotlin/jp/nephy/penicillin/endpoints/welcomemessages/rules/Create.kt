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

package jp.nephy.penicillin.endpoints.welcomemessages.rules

import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.jsonBody
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.WelcomeMessageRules
import jp.nephy.penicillin.models.WelcomeMessageRule

/**
 * Creates a new Welcome Message Rule that determines which Welcome Message will be shown in a given conversation. Returns the created rule if successful.
 * Requires a JSON POST body and Content-Type header to be set to application/json. Setting Content-Length may also be required if it is not automatically.
 * Additional rule configurations are forthcoming. For the initial beta release, the most recently created Rule will always take precedence, and the assigned Welcome Message will be displayed in the conversation.
 * See the [Welcome Messages overview](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/overview) to learn how to work with Welcome Messages.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/api-reference/new-welcome-message-rule)
 * 
 * @param id The ID of the Welcome Message that will be sent when this Rule is triggered.
 * @param options Optional. Custom parameters of this request.
 * @receiver [WelcomeMessageRules] endpoint instance.
 * @return [JsonObjectApiAction] for [WelcomeMessageRule.Single] model.
 */
fun WelcomeMessageRules.create(
    id: String,
    vararg options: Option
) = client.session.post("/1.1/direct_messages/welcome_messages/rules/new.json") {
    parameter(*options)

    jsonBody(
        "welcome_message_rule" to jsonObjectOf(
            "welcome_message_id" to id
        )
    )
}.jsonObject<WelcomeMessageRule.Single>()

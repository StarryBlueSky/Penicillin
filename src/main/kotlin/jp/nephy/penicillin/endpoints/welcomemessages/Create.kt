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

package jp.nephy.penicillin.endpoints.welcomemessages

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.WelcomeMessages
import jp.nephy.penicillin.models.WelcomeMessageSingle

/**
 * Creates a new Welcome Message that will be stored and sent in the future from the authenticating user in defined circumstances. Returns the message template if successful. Supports publishing with the same elements as Direct Messages (e.g. Quick Replies, media attachments).
 * Requires a JSON POST body and Content-Type header to be set to application/json. Setting Content-Length may also be required if it is not automatically.
 * See the [Welcome Messages overview](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/overview) to learn how to work with Welcome Messages.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/api-reference/new-welcome-message)
 * 
 * @param messageData The Message Data Object defining the content of the message template. See POST [direct_messages/events/new (message_create)](https://developer.twitter.com/en/docs/direct-messages/sending-and-receiving/api-reference/new-event) for Message Data object details.
 * @param name A human readable name for the Welcome Message. This is not displayed to the user. Max length of 100 alpha numeric characters including hyphens, underscores, spaces, hashes and at signs.
 * @param options Optional. Custom parameters of this request.
 * @receiver [WelcomeMessages] endpoint instance.
 * @return [JsonObjectApiAction] for [WelcomeMessageSingle] model.
 */
fun WelcomeMessages.create(
    messageData: JsonObject,
    name: String? = null,
    vararg options: Option
) = client.session.post("/1.1/direct_messages/welcome_messages/new.json") {
    body {
        json {
            add(
                "welcome_message" to jsonObjectOf(
                    "message_data" to messageData,
                    "name" to name
                ),
                *options
            )
        }
    }
}.jsonObject<WelcomeMessageSingle>()

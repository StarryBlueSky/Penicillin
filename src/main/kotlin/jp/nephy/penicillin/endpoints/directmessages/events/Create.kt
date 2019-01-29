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

package jp.nephy.penicillin.endpoints.directmessages.events

import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.DirectMessageEvents
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.DirectMessageEvent

/**
 * Publishes a new message_create event resulting in a Direct Message sent to a specified user from the authenticating user. Returns an event if successful. Supports publishing Direct Messages with optional Quick Reply and media attachment. Replaces behavior currently provided by [POST direct_messages/new](https://developer.twitter.com/en/docs/direct-messages/sending-and-receiving/api-reference/new-event).
 * Requires a JSON POST body and Content-Type header to be set to application/json. Setting Content-Length may also be required if it is not automatically.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/sending-and-receiving/api-reference/new-event)
 * 
 * @param userId The ID of the user who should receive the direct message.
 * @param text The Message Data Object defining the content to deliver to the recipient.
 * @param type The type of event you are posting. For Direct Messages, use message_create.
 * @param options Optional. Custom parameters of this request.
 * @receiver [DirectMessageEvents] endpoint instance.
 * @return [JsonObjectApiAction] for [DirectMessageEvent.Show] model.
 */
fun DirectMessageEvents.create(
    userId: Long,
    text: String,
    type: String = "message_create",
    vararg options: Option
) = client.session.post("/1.1/direct_messages/events/new.json") {
    body {
        json {
            add(
                "event" to jsonObjectOf(
                    "type" to type,
                    "message_create" to jsonObjectOf(
                        "target" to jsonObjectOf(
                            "recipient_id" to userId.toString()
                        ),
                        "message_data" to jsonObjectOf(
                            "text" to text
                        )
                    )
                ),
                *options
            )
        }
    }
}.jsonObject<DirectMessageEvent.Show>()

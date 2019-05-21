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
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.jsonBody
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.put
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.WelcomeMessages
import jp.nephy.penicillin.models.WelcomeMessage

/**
 * Updates a Welcome Message by the given ID. Updates to the welcome_message object are atomic. For example, if the Welcome Message currently has quick_reply defined and you only provide text, the quick_reply object will be removed from the Welcome Message.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/welcome-messages/api-reference/new-welcome-message)
 * 
 * @param id The id of the Welcome Message that should be updated.
 * @param messageData The Message Data Object defining the content of the message template. See POST [direct_messages/events/new (message_create)](https://developer.twitter.com/en/docs/direct-messages/sending-and-receiving/api-reference/new-event) for Message Data object details.
 * @param options Optional. Custom parameters of this request.
 * @receiver [WelcomeMessages] endpoint instance.
 * @return [JsonObjectApiAction] for [WelcomeMessage.Single] model.
 */
fun WelcomeMessages.update(
    id: String,
    messageData: JsonObject,
    vararg options: Option
) = client.session.put("/1.1/direct_messages/welcome_messages/update.json") {
    parameters(
        "id" to id,
        *options
    )
    
    jsonBody("message_data" to messageData)
}.jsonObject<WelcomeMessage.Single>()

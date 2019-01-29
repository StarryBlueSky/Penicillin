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

package jp.nephy.penicillin.endpoints.directmessages

import jp.nephy.penicillin.core.request.action.EmptyApiAction
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.DirectMessages
import jp.nephy.penicillin.endpoints.Option

/**
 * Displays a visual typing indicator in the recipient’s Direct Message conversation view with the sender. Each request triggers a typing indicator animation with a duration of ~3 seconds.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/direct-messages/typing-indicator-and-read-receipts/api-reference/new-typing-indicator)
 * 
 * @param recipientId The user ID of the user to receive the typing indicator.
 * @param options Optional. Custom parameters of this request.
 * @receiver [DirectMessages] endpoint instance.
 * @return [EmptyApiAction].
 */
fun DirectMessages.indicateTyping(
    recipientId: Long,
    vararg options: Option
) = client.session.post("/1.1/direct_messages/indicate_typing.json") {
    body { 
        form { 
            add(
                "recipient_id" to recipientId,
                *options
            )
        }
    }
}.empty()

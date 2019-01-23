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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.delete
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.models.DirectMessageEvent

val ApiClient.directMessageEvent: DirectMessageEvents
    get() = DirectMessageEvents(this)

class DirectMessageEvents(override val client: ApiClient): Endpoint {
    fun create(userId: String, text: String, vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/events/new.json") {
        body {
            json {
                add(
                    "event" to jsonObjectOf(
                        "type" to "message_create", "message_create" to jsonObjectOf(
                            "target" to jsonObjectOf(
                                "recipient_id" to userId
                            ), "message_data" to jsonObjectOf(
                                "text" to text
                            )
                        )
                    )
                )
                add(*options)
            }
        }
    }.jsonObject<DirectMessageEvent.Show>()

    fun delete(id: String, vararg options: Option) = client.session.delete("/1.1/direct_messages/events/destroy.json") {
        parameter("id" to id, *options)
    }.text()

    fun list(count: Int? = null, cursor: String? = null, vararg options: Option) = client.session.get("/1.1/direct_messages/events/list.json") {
        parameter("count" to count, "cursor" to cursor, *options)
    }.jsonObject<DirectMessageEvent.List>()

    fun show(id: String, vararg options: Option) = client.session.get("/1.1/direct_messages/events/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<DirectMessageEvent.Show>()
}

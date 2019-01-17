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

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.DirectMessage

class DirectMessages(override val client: PenicillinClient): Endpoint {
    @Deprecated("Legacy Direct Message API retired on September 17th, 2018.", replaceWith = ReplaceWith("jp.nephy.endpoints.DirectMessageEvent"))
    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<DirectMessage>()

    @Deprecated("Legacy Direct Message API retired on September 17th, 2018.", replaceWith = ReplaceWith("jp.nephy.endpoints.DirectMessageEvent"))
    fun list(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/direct_messages.json") {
            parameter("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
        }.jsonArray<DirectMessage>()

    @Deprecated("Legacy Direct Message API retired on September 17th, 2018.", replaceWith = ReplaceWith("jp.nephy.endpoints.DirectMessageEvent"))
    fun sentMessages(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, page: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/direct_messages/sent.json") {
            parameter("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "page" to page, *options)
        }.jsonArray<DirectMessage>()

    @Deprecated("Legacy Direct Message API retired on September 17th, 2018.", replaceWith = ReplaceWith("jp.nephy.endpoints.DirectMessageEvent"))
    fun create(text: String, userId: Long? = null, screenName: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/direct_messages/new.json") {
        body {
            form {
                add("text" to text, "user_id" to userId, "screen_name" to screenName, *options)
            }
        }
    }.jsonObject<DirectMessage>()

    @Deprecated("Legacy Direct Message API retired on September 17th, 2018.", replaceWith = ReplaceWith("jp.nephy.endpoints.DirectMessageEvent"))
    fun delete(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/direct_messages/destroy.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<DirectMessage>()
}

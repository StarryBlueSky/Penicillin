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
import jp.nephy.penicillin.endpoints.parameters.CollectionCreationTimelineOrder

val PenicillinClient.collections: Collections
    get() = Collections(this)

class Collections(override val client: PenicillinClient): Endpoint {
    fun show(id: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/show.json") {
        parameter("id" to id, *options)
    }.empty()

    fun list(userId: Long, tweetId: Long? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/list.json") {
        parameter("user_id" to userId, "tweet_id" to tweetId, "count" to count, *options)
    }.empty()

    fun list(screenName: String, tweetId: Long? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/list.json") {
        parameter("screen_name" to screenName, "tweet_id" to tweetId, "count" to count, *options)
    }.empty()

    fun create(name: String, description: String? = null, url: String? = null, timelineOrder: CollectionCreationTimelineOrder? = null, vararg options: Pair<String, Any?>) =
        client.session.post("/1.1/collections/create.json") {
            body {
                form {
                    add("name" to name, "description" to description, "url" to url, "timeline_order" to timelineOrder?.value, *options)
                }
            }
        }.empty()

    fun destroy(id: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/destroy.json") {
        body {
            form {
                add("id" to id, *options)
            }
        }
    }.empty()

    fun update(id: String, name: String? = null, description: String? = null, url: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/update.json") {
        body {
            form {
                add("id" to id, "name" to name, "description" to description, "url" to url, *options)
            }
        }
    }.empty()
}

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

import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post

val ApiClient.collectionEntries: CollectionEntries
    get() = CollectionEntries(this)

class CollectionEntries(override val client: ApiClient): Endpoint {
    fun entries(id: String, count: Int? = null, maxPosition: Int? = null, minPosition: Int? = null, vararg options: Option) = client.session.get("/1.1/collections/entries.json") {
        parameter("id" to id, "count" to count, "max_position" to maxPosition, "min_position" to minPosition, *options)
    }.empty()

    fun add(id: String, tweetId: Long, relativeTo: Long? = null, above: Boolean? = null, vararg options: Option) = client.session.post("/1.1/collections/entries/add.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
            }
        }
    }.empty()

    fun curate(vararg options: Pair<String, String>) = client.session.post("/1.1/collections/entries/curate.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun move(id: String, tweetId: Long, relativeTo: Long, above: Boolean? = null, vararg options: Option) = client.session.post("/1.1/collections/entries/move.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
            }
        }
    }.empty()

    fun remove(id: String, tweetId: Long, vararg options: Option) = client.session.post("/1.1/collections/entries/remove.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, *options)
            }
        }
    }.empty()
}

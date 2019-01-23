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
import jp.nephy.penicillin.models.Status

val ApiClient.favorites: Favorites
    get() = Favorites(this)

class Favorites(override val client: ApiClient): Endpoint {
    fun list(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Option) =
            client.session.get("/1.1/favorites/list.json") {
                parameter("count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
            }.jsonArray<Status>()

    fun list(userId: Long, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Option) =
        client.session.get("/1.1/favorites/list.json") {
            parameter("user_id" to userId, "count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
        }.jsonArray<Status>()

    fun list(screenName: String, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Option) =
            client.session.get("/1.1/favorites/list.json") {
                parameter("screen_name" to screenName, "count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
            }.jsonArray<Status>()

    fun create(id: Long, includeEntities: Boolean? = null, vararg options: Option) = client.session.post("/1.1/favorites/create.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<Status>()

    fun destroy(id: Long, includeEntities: Boolean? = null, vararg options: Option) = client.session.post("/1.1/favorites/destroy.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<Status>()
}

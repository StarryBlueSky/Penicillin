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
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorUsers

class Followers(override val client: PenicillinClient): Endpoint {
    fun listIds(stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/followers/ids.json") {
                parameter("stringify_ids" to stringifyIds, "count" to count, *options)
            }.cursorJsonObject<CursorIds>()

    fun listIds(userId: Long, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/followers/ids.json") {
            parameter("user_id" to userId, "stringify_ids" to stringifyIds, "count" to count, *options)
        }.cursorJsonObject<CursorIds>()

    fun listIds(screenName: String, stringifyIds: Boolean? = null, count: Int? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/followers/ids.json") {
            parameter("screen_name" to screenName, "stringify_ids" to stringifyIds, "count" to count, *options)
        }.cursorJsonObject<CursorIds>()

    fun list(count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/followers/list.json") {
                parameter("count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
            }.cursorJsonObject<CursorUsers>()

    fun list(userId: Long, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/followers/list.json") {
            parameter("user_id" to userId, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
        }.cursorJsonObject<CursorUsers>()

    fun list(screenName: String, count: Int? = null, skipStatus: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/followers/list.json") {
                parameter("screen_name" to screenName, "count" to count, "skip_status" to skipStatus, "include_user_entities" to includeUserEntities, *options)
            }.cursorJsonObject<CursorUsers>()
}

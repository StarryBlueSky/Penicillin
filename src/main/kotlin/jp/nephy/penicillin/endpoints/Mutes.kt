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
import jp.nephy.penicillin.models.User

val PenicillinClient.mutes: Mutes
    get() = Mutes(this)

class Mutes(override val client: PenicillinClient): Endpoint {
    fun listIds(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/mutes/users/ids.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    fun list(includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/mutes/users/list.json") {
        parameter("include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.cursorJsonObject<CursorUsers>()

    fun create(screenName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/mutes/users/create.json") {
        body {
            form {
                add("screen_name" to screenName, *options)
            }
        }
    }.jsonObject<User>()

    fun create(userId: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/mutes/users/create.json") {
        body {
            form {
                add("user_id" to userId, *options)
            }
        }
    }.jsonObject<User>()

    fun destroy(screenName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/mutes/users/destroy.json") {
        body {
            form {
                add("screen_name" to screenName, *options)
            }
        }
    }.jsonObject<User>()

    fun destroy(userId: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/mutes/users/destroy.json") {
        body {
            form {
                add("user_id" to userId, *options)
            }
        }
    }.jsonObject<User>()
}

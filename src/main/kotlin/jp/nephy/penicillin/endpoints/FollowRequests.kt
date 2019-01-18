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
import jp.nephy.penicillin.models.User

val PenicillinClient.followRequests: FollowRequests
    get() = FollowRequests(this)

class FollowRequests(override val client: PenicillinClient): Endpoint {
    fun received(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/incoming.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    fun sent(stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/friendships/outgoing.json") {
        parameter("stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    @PrivateEndpoint
    fun accept(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/friendships/accept.json") {
        body {
            form {
                add(
                    "ext" to "mediaColor",
                    "include_entities" to "1",
                    "include_profile_interstitial_type" to "true",
                    "include_profile_location" to "true",
                    "include_user_entities" to "true",
                    "include_user_hashtag_entities" to "true",
                    "include_user_mention_entities" to "true",
                    "include_user_symbol_entities" to "true",
                    "screen_name" to screenName,
                    "user_id" to userId,
                    *options
                )
            }
        }
    }.jsonObject<User>()
}

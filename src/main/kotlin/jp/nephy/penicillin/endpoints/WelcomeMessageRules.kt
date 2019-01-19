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
import jp.nephy.penicillin.core.session.delete
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.models.WelcomeMessageRule

val PenicillinClient.welcomeMessageRules: WelcomeMessageRules
    get() = WelcomeMessageRules(this)

class WelcomeMessageRules(override val client: PenicillinClient): Endpoint {
    fun delete(id: Long, vararg options: Pair<String, String>) = client.session.delete("/1.1/direct_messages/welcome_messages/rules/destroy.json") {
        parameter("id" to id, *options)
    }.empty()

    fun create(vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/welcome_messages/rules/new.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/rules/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<WelcomeMessageRule>()

    fun list(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/rules/list.json") {
        parameter("count" to count, *options)
    }.jsonArray<WelcomeMessageRule>()
}

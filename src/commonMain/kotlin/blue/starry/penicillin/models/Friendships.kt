/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.model
import blue.starry.jsonkt.delegation.string
import blue.starry.jsonkt.delegation.stringList
import blue.starry.penicillin.core.session.ApiClient
public object Friendships {
    public data class Show(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val relationship: Relationship by model { Relationship(it, client) }
    }

    public data class Lookup(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val connections: List<String> by stringList
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val name: String by string
        public val screenName: String by string("screen_name")
    }
}

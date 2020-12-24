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
import blue.starry.jsonkt.delegation.boolean
import blue.starry.jsonkt.delegation.modelList
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient


public object Webhook {
    public data class Model(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val id: String by string
        public val url: String by string
        public val valid: Boolean by boolean
        public val createdAt: String by string("created_at")
    }

    public data class List(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val environments: kotlin.collections.List<Environment> by modelList { Environment(it, client) }

        public data class Environment(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val name: String by string("environment_name")
            public val webhooks: kotlin.collections.List<Model> by modelList { Model(it, client) }
        }
    }
}

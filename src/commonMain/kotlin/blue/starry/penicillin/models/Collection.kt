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
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.JsonArray


public object Collection {
    public data class Model(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val response: Response by model { Response(it, client) }
        public val objects: JsonObject by jsonObject
        
        public data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val timelineId: String by string("timeline_id")
        }
    }
    
    public data class DestroyResult(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val destroyed: Boolean by boolean
    }
    
    public data class List(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val objects: JsonObject by jsonObject
        public val response: Response by model { Response(it, client) }
        
        public data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val results: kotlin.collections.List<Model.Response> by modelList { Model.Response(it, client) }
            public val cursors: Cursors by model { Cursors(it, client) }
            
            public data class Cursors(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val nextCursor: String by string("next_cursor")
            }
        }
    }
    
    public object Entry {
        public data class Result(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val objects: JsonObject by jsonObject
            public val response: Response by model { Response(it, client) }
            
            public data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val errors: JsonArray by jsonArray
            }
        }
    }
}

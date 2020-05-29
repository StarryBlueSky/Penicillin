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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient


object Collection {
    data class Model(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val response by model { Response(it, client) }
        val objects by jsonObject
        
        data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val timelineId by string("timeline_id")
        }
    }
    
    data class DestroyResult(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val destroyed by boolean
    }
    
    data class List(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val objects by jsonObject
        val response by model { Response(it, client) }
        
        data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val results by modelList { Model.Response(it, client) }
            val cursors by model { Cursors(it, client) }
            
            data class Cursors(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val nextCursor by string("next_cursor")
            }
        }
    }
    
    object Entry {
        data class Result(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val objects by jsonObject
            val response by model { Response(it, client) }
            
            data class Response(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val errors by jsonArray
            }
        }
    }
}

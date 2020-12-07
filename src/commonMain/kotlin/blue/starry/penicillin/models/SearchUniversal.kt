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
import blue.starry.jsonkt.jsonObjectOrNull
import blue.starry.jsonkt.parseObject
import blue.starry.jsonkt.parseObjectOrNull
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject


data class SearchUniversal(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val metadata by model("metadata") { Metadata(it, client) }
    val statuses by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.mapNotNull { json ->
            json["status"]?.parseObjectOrNull { obj -> Status(obj, client) }
        }
    }
    val userGalleries by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.mapNotNull { json ->
            json["user_gallery"]?.parseObjectOrNull { obj ->
                UserGallery(obj, client)
            }
        }
    }

    data class Status(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val metadata by model { StatusMetadata(it, client) }
        val data by model { blue.starry.penicillin.models.Status(it, client) }

        data class StatusMetadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val resultType by string("result_type")
        }
    }

    data class Metadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val cursor by string
        val refreshIntervalInSec by int("refresh_interval_in_sec")
    }

    data class UserGallery(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val metadata by model { Metadata(it, client) }
        val data by modelList { Data(it, client) }

        data class Metadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val resultType by string("result_type")
        }

        data class Data(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val metadata by model { Metadata(it, client) }
            val data by model { User(it, client) }

            data class Metadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val resultType by string("result_type")
            }
        }
    }
}

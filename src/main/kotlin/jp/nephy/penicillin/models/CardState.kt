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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.get
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.PenicillinClient

data class CardState(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
    private val card by jsonObject
    val name by card.byString
    val url by card.byString
    val cardTypeUrl by card.byString("card_type_url")
    val cardPlatform by card.byModel<Platform>(null, client)
    val data by card.byModel<Data>("binding_values", client)

    data class Platform(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        private val platform by jsonObject
        private val device by platform.byJsonObject
        private val audience by platform.byJsonObject
        val deviceName by device.byString("name")
        val deviceVersion by device.byString("version")
        val audienceName by audience.byString("name")
        val audienceBucket by audience.byNullableString("bucket")
    }

    data class Data(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        val choices: Map<String, Int>
            get() = (1..5).filter { json.contains("choice${it}_label") }.map {
                json["choice${it}_label"]["string_value"].string to if (json.contains("choice${it}_count")) {
                    json["choice${it}_count"]["string_value"].string.toInt()
                } else {
                    0
                }
            }.toMap()
        val isFinal by json["counts_are_final"].jsonObject.byBoolean("boolean_value")
        val endAt by json["end_datetime_utc"].jsonObject.byString("string_value")
        val updateAt by json["last_updated_datetime_utc"].jsonObject.byString("string_value")
        val minutes by json["duration_minutes"].jsonObject.byString("string_value")
    }
}

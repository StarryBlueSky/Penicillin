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
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.nullableString
import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.nullablePenicillinModel
import jp.nephy.penicillin.extensions.penicillinModel

data class CardState(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val card by penicillinModel<Card>()

    data class Card(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val name by string
        val url by string
        val cardTypeUrl by string("card_type_url")
        val bindingValues by penicillinModel<BindingValues>("binding_values")
        val cardPlatform by penicillinModel<CardPlatform>("card_platform")

        data class BindingValues(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val choice1Label by nullablePenicillinModel<StringValue>("choice1_label")
            val choice2Label by nullablePenicillinModel<StringValue>("choice2_label")
            val choice3Label by nullablePenicillinModel<StringValue>("choice3_label")
            val choice4Label by nullablePenicillinModel<StringValue>("choice4_label")
            
            val choice1Count by nullablePenicillinModel<StringValue>("choice1_count")
            val choice2Count by nullablePenicillinModel<StringValue>("choice2_count")
            val choice3Count by nullablePenicillinModel<StringValue>("choice3_count")
            val choice4Count by nullablePenicillinModel<StringValue>("choice4_count")
            
            val selectedChoice by nullablePenicillinModel<StringValue>("selected_choice")
            val lastUpdatedDatetimeUtc by nullablePenicillinModel<StringValue>("last_updated_datetime_utc")
            val endDatetimeUtc by nullablePenicillinModel<StringValue>("end_datetime_utc")
            val countsAreFinal by nullablePenicillinModel<BooleanValue>("counts_are_final")
            val durationMinutes by nullablePenicillinModel<StringValue>("duration_minutes")
            val api by nullablePenicillinModel<StringValue>()
            val cardUrl by nullablePenicillinModel<StringValue>("card_url")

            data class StringValue(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val type by string
                val value by string("string_value")
            }
            
            data class BooleanValue(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val type by string
                val value by boolean("boolean_value")
            }
        }
        
        data class CardPlatform(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val platform by penicillinModel<Platform>()
            
            data class Platform(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val device by penicillinModel<Device>()
                val audience by penicillinModel<Audience>()
                
                data class Device(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    val name by string
                    val version by string
                }
                
                data class Audience(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    val name by string
                    val bucket by nullableString
                }
            }
        }
    }
}

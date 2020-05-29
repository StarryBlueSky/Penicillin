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

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient

data class CardState(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val card by model { Card(it, client) }

    data class Card(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val name by string
        val url by string
        val cardTypeUrl by string("card_type_url")
        val bindingValues by model("binding_values") { BindingValues(it, client) }
        val cardPlatform by model("card_platform") { CardPlatform(it, client) }

        data class BindingValues(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val choice1Label by nullableModel("choice1_label") { StringValue(it, client) }
            val choice2Label by nullableModel("choice2_label") { StringValue(it, client) }
            val choice3Label by nullableModel("choice3_label") { StringValue(it, client) }
            val choice4Label by nullableModel("choice4_label") { StringValue(it, client) }
            
            val choice1Count by nullableModel("choice1_count") { StringValue(it, client) }
            val choice2Count by nullableModel("choice2_count") { StringValue(it, client) }
            val choice3Count by nullableModel("choice3_count") { StringValue(it, client) }
            val choice4Count by nullableModel("choice4_count") { StringValue(it, client) }
            
            val selectedChoice by nullableModel("selected_choice") { StringValue(it, client) }
            val lastUpdatedDatetimeUtc by nullableModel("last_updated_datetime_utc") { StringValue(it, client) }
            val endDatetimeUtc by nullableModel("end_datetime_utc") { StringValue(it, client) }
            val countsAreFinal by nullableModel("counts_are_final") { BooleanValue(it, client) }
            val durationMinutes by nullableModel("duration_minutes") { StringValue(it, client) }
            val api by nullableModel { StringValue(it, client) }
            val cardUrl by nullableModel("card_url") { StringValue(it, client) }

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
            val platform by model { Platform(it, client) }
            
            data class Platform(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val device by model { Device(it, client) }
                val audience by model { Audience(it, client) }
                
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

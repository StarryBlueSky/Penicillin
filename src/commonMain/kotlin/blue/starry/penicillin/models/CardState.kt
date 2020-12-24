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

public data class CardState(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val card: Card by model { Card(it, client) }

    public data class Card(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val name: String by string
        public val url: String by string
        public val cardTypeUrl: String by string("card_type_url")
        public val bindingValues: BindingValues by model("binding_values") { BindingValues(it, client) }
        public val cardPlatform: CardPlatform by model("card_platform") { CardPlatform(it, client) }

        public data class BindingValues(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val choice1Label: StringValue? by nullableModel("choice1_label") { StringValue(it, client) }
            public val choice2Label: StringValue? by nullableModel("choice2_label") { StringValue(it, client) }
            public val choice3Label: StringValue? by nullableModel("choice3_label") { StringValue(it, client) }
            public val choice4Label: StringValue? by nullableModel("choice4_label") { StringValue(it, client) }
            
            public val choice1Count: StringValue? by nullableModel("choice1_count") { StringValue(it, client) }
            public val choice2Count: StringValue? by nullableModel("choice2_count") { StringValue(it, client) }
            public val choice3Count: StringValue? by nullableModel("choice3_count") { StringValue(it, client) }
            public val choice4Count: StringValue? by nullableModel("choice4_count") { StringValue(it, client) }
            
            public val selectedChoice: StringValue? by nullableModel("selected_choice") { StringValue(it, client) }
            public val lastUpdatedDatetimeUtc: StringValue? by nullableModel("last_updated_datetime_utc") { StringValue(it, client) }
            public val endDatetimeUtc: StringValue? by nullableModel("end_datetime_utc") { StringValue(it, client) }
            public val countsAreFinal: BooleanValue? by nullableModel("counts_are_final") { BooleanValue(it, client) }
            public val durationMinutes: StringValue? by nullableModel("duration_minutes") { StringValue(it, client) }
            public val api: StringValue? by nullableModel { StringValue(it, client) }
            public val cardUrl: StringValue? by nullableModel("card_url") { StringValue(it, client) }

            public data class StringValue(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val type: String by string
                public val value: String by string("string_value")
            }
            
            public data class BooleanValue(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val type: String by string
                public val value: Boolean by boolean("boolean_value")
            }
        }
        
        public data class CardPlatform(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val platform: Platform by model { Platform(it, client) }
            
            public data class Platform(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val device: Device by model { Device(it, client) }
                public val audience: Audience by model { Audience(it, client) }
                
                public data class Device(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    public val name: String by string
                    public val version: String by string
                }
                
                public data class Audience(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    public val name: String by string
                    public val bucket: String? by nullableString
                }
            }
        }
    }
}

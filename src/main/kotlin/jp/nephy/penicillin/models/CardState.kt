@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.get
import jp.nephy.jsonkt.string

data class CardState(override val json: JsonObject): PenicillinModel {
    private val card by jsonObject
    val name by card.byString
    val url by card.byString
    val cardTypeUrl by card.byString("card_type_url")
    val cardPlatform by card.byModel<Platform>()
    val data by card.byModel<Data>(key = "binding_values")

    data class Platform(override val json: JsonObject): PenicillinModel {
        private val platform by jsonObject
        private val device by platform.byJsonObject
        private val audience by platform.byJsonObject
        val deviceName by device.byString("name")
        val deviceVersion by device.byString("version")
        val audienceName by audience.byString("name")
        val audienceBucket by audience.byNullableString("bucket")
    }

    data class Data(override val json: JsonObject): PenicillinModel {
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

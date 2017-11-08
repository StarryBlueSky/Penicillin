package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement

@Suppress("UNUSED")
class CardBindingValue(val json: JsonElement) {
    val choices = (1..5).filter { json.obj.contains("choice${it}_label") }.map {
        json["choice${it}_label"]["string_value"].asString to if (json.obj.contains("choice${it}_count")) {
            json["choice${it}_count"]["string_value"].asString
        } else {
            "0"
        }.toInt()
    }.toMap()

    val isFinal by json["counts_are_final"].byBool("boolean_value")
    val endAt by json["end_datetime_utc"].byString("string_value")
    val updateAt by json["last_updated_datetime_utc"].byString("string_value")
    val minutes by json["duration_minutes"].byString("string_value")
}

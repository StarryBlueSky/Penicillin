package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byStringArray

class StatusWithheld(val json: JsonElement) {
    val userId by json["status_withheld"].byLong("user_id")
    val id by json["status_withheld"].byLong
    val timestampMs by json["status_withheld"].byString("timestamp_ms")
    val withheldInCountries by json["status_withheld"].byStringArray("withheld_in_countries")
}
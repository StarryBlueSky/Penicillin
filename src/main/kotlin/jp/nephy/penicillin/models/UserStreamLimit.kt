package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


data class UserStreamLimit(override val json: JsonObject): PenicillinModel {
    val track by json["limit"].byInt
    val timestampMs by json["limit"].byString("timestamp_ms")
}

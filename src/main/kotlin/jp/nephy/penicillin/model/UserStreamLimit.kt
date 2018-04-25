package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class UserStreamLimit(override val json: JsonObject): JsonModel {
    val track by json["limit"].byInt
    val timestampMs by json["limit"].byString("timestamp_ms")
}

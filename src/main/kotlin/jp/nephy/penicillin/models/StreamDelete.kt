package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.get


class StreamDelete(override val json: JsonObject): PenicillinModel {
    val timestampMs by json["delete"].byString("timestamp_ms")
    val statusId by json["delete"]["status"].byLong("id")
    val statusIdStr by json["delete"]["status"].byString("id_str")
    val userId by json["delete"]["status"].byLong("user_id")
    val userIdStr by json["delete"]["status"].byString("user_id_str")
}

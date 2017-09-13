package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

class Delete(val json: JsonElement) {
    val timestampMs by json["delete"].byString("timestamp_ms")
    val statusId by json["delete"]["status"].byLong("id")
    val statusIdStr by json["delete"]["status"].byString("id_str")
    val userId by json["delete"]["status"].byLong("user_id")
    val userIdStr by json["delete"]["status"].byString("user_id_str")
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

class ScrubGeo(val json: JsonElement) {
    val userId by json["scrub_geo"].byLong("user_id")
    val userIdStr by json["scrub_geo"].byString("user_id_str")
    val upToStatusId by json["scrub_geo"].byLong("up_to_status_id")
    val upToStatusIdStr by json["scrub_geo"].byString("up_to_status_id_str")
    val timestampMs by json["scrub_geo"].byString("timestamp_ms")
}
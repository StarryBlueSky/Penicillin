package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

@Suppress("UNUSED")
class Limit(val json: JsonElement) {
    val track by json["limit"].byInt
    val timestampMs by json["limit"].byString("timestamp_ms")
}
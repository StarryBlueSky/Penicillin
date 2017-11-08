package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class TrendMetadata(val json: JsonElement) {
    val contextMode by json.byString("context_mode")
    val refreshIntervalMillis by json.byInt("refresh_interval_millis")
    val timestamp by json.byLong
}

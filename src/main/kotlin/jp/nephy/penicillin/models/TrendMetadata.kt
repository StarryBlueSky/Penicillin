package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString


data class TrendMetadata(override val json: JsonObject): PenicillinModel {
    val contextMode by json.byString("context_mode")
    val refreshIntervalMillis by json.byInt("refresh_interval_millis")
    val timestamp by json.byLong
}

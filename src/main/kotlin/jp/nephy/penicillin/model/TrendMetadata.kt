package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class TrendMetadata(override val json: JsonObject): JsonModel {
    val contextMode by json.byString("context_mode")
    val refreshIntervalMillis by json.byInt("refresh_interval_millis")
    val timestamp by json.byLong
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong

@Suppress("UNUSED")
class ApplicationRateLimit(override val json: JsonObject): JsonModel {
    val limit by json.byInt
    val remaining by json.byInt
    val reset by json.byLong
}

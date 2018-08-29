package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong


data class ApplicationRateLimit(override val json: JsonObject): PenicillinModel {
    val limit by json.byInt
    val remaining by json.byInt
    val reset by json.byLong
}

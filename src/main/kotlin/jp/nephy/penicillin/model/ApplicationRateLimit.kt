package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byLong
import com.google.gson.JsonElement

@Suppress("UNUSED")
class ApplicationRateLimit(val json: JsonElement) {
    val limit by json.byInt
    val remaining by json.byInt
    val reset by json.byLong
}
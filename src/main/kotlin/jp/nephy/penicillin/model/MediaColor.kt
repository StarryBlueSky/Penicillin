package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byObject
import com.google.gson.JsonElement

@Suppress("UNUSED")
class MediaColor(val json: JsonElement) {
    val r by json.byObject
    val ttl by json.byInt
}

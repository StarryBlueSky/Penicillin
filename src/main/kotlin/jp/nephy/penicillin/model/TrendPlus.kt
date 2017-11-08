package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class TrendPlus(val json: JsonElement) {
    val id by json.byLong
    val metadata by json.byModel<TrendMetadata>()
    val trends by json.byList<TrendType>("modules")
}
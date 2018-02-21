package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byJsonObject

@Suppress("UNUSED")
class MediaColor(override val json: JsonObject): JsonModel {
    val r by json.byJsonObject
    val ttl by json.byInt
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byUrl

class GeoQuery(override val json: JsonObject): JsonModel {
    val params by json.byJsonObject
    val type by json.byString
    val url by json.byUrl
}

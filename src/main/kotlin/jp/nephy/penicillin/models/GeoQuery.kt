package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byString

data class GeoQuery(override val json: JsonObject): PenicillinModel {
    val params by json.byJsonObject
    val type by json.byString
    val url by json.byString
}

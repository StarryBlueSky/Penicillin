package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byJsonObject


class MediaColor(override val json: JsonObject): PenicillinModel {
    val r by json.byJsonObject
    val ttl by json.byInt
}

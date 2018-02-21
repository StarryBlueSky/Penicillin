package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Tos(override val json: JsonObject): JsonModel {
    val tos by json.byString
}

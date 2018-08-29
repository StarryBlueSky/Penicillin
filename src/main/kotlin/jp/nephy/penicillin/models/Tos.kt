package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString


data class Tos(override val json: JsonObject): PenicillinModel {
    val tos by json.byString
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

class Language(override val json: JsonObject): JsonModel {
    val code by json.byString
    val status by json.byString
    val name by json.byString
}

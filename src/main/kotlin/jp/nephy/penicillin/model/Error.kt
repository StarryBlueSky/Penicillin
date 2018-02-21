package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString

class Error(override val json: JsonObject): JsonModel {
    val code by json.byInt
    val name by json.byNullableString
    val message by json.byString
}

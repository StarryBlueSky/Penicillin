package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

class SearchToken(override val json: JsonObject): JsonModel {
    val token by json.byString
}

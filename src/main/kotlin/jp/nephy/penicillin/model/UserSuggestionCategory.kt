package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class UserSuggestionCategory(override val json: JsonObject): JsonModel {
    val name by json.byString
    val size by json.byInt
    val slug by json.byString
}

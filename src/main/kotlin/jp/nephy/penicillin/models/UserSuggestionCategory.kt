package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class UserSuggestionCategory(override val json: JsonObject): PenicillinModel {
    val name by json.byString
    val size by json.byInt
    val slug by json.byString
}

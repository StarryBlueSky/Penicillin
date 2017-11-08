package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class UserSuggestionCategory(val json: JsonElement) {
    val name by json.byString
    val size by json.byInt
    val slug by json.byString
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class SearchToken(val json: JsonElement) {
    val token by json.byString
}

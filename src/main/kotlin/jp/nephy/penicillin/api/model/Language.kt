package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class Language(val json: JsonElement) {
    val code by json.byString
    val status by json.byString
    val name by json.byString
}

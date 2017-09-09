package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class HelpLanguages(val json: JsonElement) {
    val code by json.byString // "fr"
    val status by json.byString // "production"
    val name by json.byString // "French"
}

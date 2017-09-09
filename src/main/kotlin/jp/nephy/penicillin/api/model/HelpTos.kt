package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class HelpTos(val json: JsonElement) {
    val tos by json.byString
}

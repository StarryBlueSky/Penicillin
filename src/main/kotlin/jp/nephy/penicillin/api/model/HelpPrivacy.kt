package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class HelpPrivacy(val json: JsonElement) {
    val privacy by json.byString
}

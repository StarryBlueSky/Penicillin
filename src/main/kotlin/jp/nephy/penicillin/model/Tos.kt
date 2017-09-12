package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class Tos(val json: JsonElement) {
    val tos by json.byString
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class Tos(val json: JsonElement) {
    val tos by json.byString
}

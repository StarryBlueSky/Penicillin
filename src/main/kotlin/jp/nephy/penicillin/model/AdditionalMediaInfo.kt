package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class AdditionalMediaInfo(val json: JsonElement) {
    val title by json.byString
    val description by json.byString
    val embeddable by json.byBool
}

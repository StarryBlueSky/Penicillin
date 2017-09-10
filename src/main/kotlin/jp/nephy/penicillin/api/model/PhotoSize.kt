package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class PhotoSize(val json: JsonElement) {
    val h by json.byInt
    val resize by json.byString
    val w by json.byInt
}

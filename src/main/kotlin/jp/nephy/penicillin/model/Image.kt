package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class Image(val json: JsonElement) {
    val imageType by json.byString("image_type")
    val w by json.byInt
    val h by json.byInt
}


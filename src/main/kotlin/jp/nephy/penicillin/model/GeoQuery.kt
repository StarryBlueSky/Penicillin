package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byObject
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import java.net.URL

class GeoQuery(val json: JsonElement) {
    val params by json.byObject
    val type by json.byString
    val url by json.byConverter<String, URL>()
}
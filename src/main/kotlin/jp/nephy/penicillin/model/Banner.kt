package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import java.net.URL

class Banner(val json: JsonElement) {
    val h by json.byInt
    val w by json.byInt
    val url by json.byConverter<String, URL>()
}

package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byList
import java.net.URL

class URLEntity(val json: JsonElement) {
    val url by json.byConverter<String, URL>()
    val expandedUrl by json.byConverter<String, URL>("expanded_url")
    val displayUrl by json.byConverter<String, URL>("display_url")
    val indices by json.byList<Int>()
}

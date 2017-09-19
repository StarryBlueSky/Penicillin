package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import java.net.URL

class VideoFile(val json: JsonElement) {
    val bitrate by json.byNullableInt
    val contentType by json.byString("content_type")
    val url by json.byConverter<String, URL>()
}

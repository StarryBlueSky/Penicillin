package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byString


data class VideoFile(override val json: JsonObject): PenicillinModel {
    val bitrate by json.byNullableInt
    val contentType by json.byString("content_type")
    val url by json.byString
}

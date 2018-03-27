package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byUrl


class VideoFile(override val json: JsonObject): JsonModel {
    val bitrate by json.byNullableInt
    val contentType by json.byString("content_type")
    val url by json.byUrl
}

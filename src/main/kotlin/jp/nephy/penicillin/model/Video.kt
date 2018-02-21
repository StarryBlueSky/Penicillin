package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Video(override val json: JsonObject): JsonModel {
    val videoType by json.byString("video_type")
}

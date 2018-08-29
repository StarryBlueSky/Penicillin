package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString


data class Video(override val json: JsonObject): PenicillinModel {
    val videoType by json.byString("video_type")
}

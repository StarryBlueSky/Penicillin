package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class Video(val json: JsonElement) {
    val videoType by json.byString("video_type")
}

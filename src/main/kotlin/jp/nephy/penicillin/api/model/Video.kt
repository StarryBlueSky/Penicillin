package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class Video(val json: JsonElement) {
    val videoType by json.byString("video_type")
}

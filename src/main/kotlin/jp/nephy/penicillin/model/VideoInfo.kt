package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class VideoInfo(val json: JsonElement) {
    val durationMillis by json.byInt("duration_millis")
    val aspectRatio by json.byList<Int>()
    val variants by json.byList<VideoFile>()
}

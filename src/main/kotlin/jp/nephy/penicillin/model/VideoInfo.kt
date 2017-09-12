package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byIntArray
import jp.nephy.penicillin.converter.byVideoFileArray

class VideoInfo(val json: JsonElement) {
    val durationMillis by json.byInt("duration_millis")
    val aspectRatio by json.byIntArray
    val variants by json.byVideoFileArray
}

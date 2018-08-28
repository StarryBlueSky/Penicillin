package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byModelList


class VideoInfo(override val json: JsonObject): PenicillinModel {
    val durationMillis by json.byInt("duration_millis")
    val aspectRatio by json.byIntList
    val variants by json.byModelList<VideoFile>()
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class VideoInfo(override val json: JsonObject): PenicillinModel {
    val durationMillis by int("duration_millis")
    val aspectRatio by intList
    val variants by modelList<VideoFile>()
}

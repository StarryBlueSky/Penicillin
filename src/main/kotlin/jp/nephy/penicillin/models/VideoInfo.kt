@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class VideoInfo(override val json: ImmutableJsonObject): PenicillinModel {
    val durationMillis by int("duration_millis")
    val aspectRatio by intList
    val variants by modelList<VideoFile>()
}

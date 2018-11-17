@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Video(override val json: JsonObject): PenicillinModel {
    val videoType by string("video_type")
}

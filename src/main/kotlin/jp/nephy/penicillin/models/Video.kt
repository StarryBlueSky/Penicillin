@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Video(override val json: ImmutableJsonObject): PenicillinModel {
    val videoType by string("video_type")
}

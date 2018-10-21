@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class VideoFile(override val json: ImmutableJsonObject): PenicillinModel {
    val bitrate by nullableInt
    val contentType by string("content_type")
    val url by string
}

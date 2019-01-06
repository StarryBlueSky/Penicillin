@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Media(override val json: JsonObject): PenicillinModel {
    val expiresAfterSecs by nullableInt("expires_after_secs")
    val mediaId by long("media_id")
    val mediaIdString by string("media_id_string")
    val mediaKey by nullableString("media_key")
    val processingInfo by model<ProcessingInfo>(key = "processing_info")
    val size by nullableInt
    val image by model<Image?>()
    val video by model<Video?>()

    data class ProcessingInfo(override val json: JsonObject): PenicillinModel {
        val checkAfterSecs by nullableInt("check_after_secs")
        val error by model<Error?>()
        val progressPercent by nullableInt("progress_percent")
        val state by string

        data class Error(override val json: JsonObject): PenicillinModel {
            val code by int
            val name by nullableString
            val message by string
        }
    }

    data class Image(override val json: JsonObject): PenicillinModel {
        val imageType by string("image_type")
        val w by int
        val h by int
    }

    data class Video(override val json: JsonObject): PenicillinModel {
        val videoType by string("video_type")
    }
}

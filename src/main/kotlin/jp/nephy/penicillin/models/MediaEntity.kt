@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class MediaEntity(override val json: JsonObject): PenicillinModel {
    val additionalMediaInfo by model<AdditionalMediaInfo?>(key = "additional_media_info")
    val displayUrl by string("display_url")
    val expandedUrl by string("expanded_url")
    val extAltText by nullableString("ext_alt_text")
    val features by model<Feature>()
    val id by long
    val idStr by string("id_str")
    val indices by intList
    val mediaUrl by string("media_url")
    val mediaUrlHttps by string("media_url_https")
    val sizes by model<Photo>()
    val sourceStatusId by nullableLong("source_status_id")
    val sourceStatusIdStr by nullableString("source_status_id_str")
    val type by string
    val url by string
    val videoInfo by model<VideoInfo?>(key = "video_info")

    data class AdditionalMediaInfo(override val json: JsonObject): PenicillinModel {
        val title by string
        val description by string
        val embeddable by boolean
    }

    data class Feature(override val json: JsonObject): PenicillinModel {
        val large by model<Size>()
        val medium by model<Size>()
        val orig by model<Size>()
        val small by model<Size>()

        data class Size(override val json: JsonObject): PenicillinModel {
            val faces by modelList<FaceCoordinate>()
        }
    }

    data class VideoInfo(override val json: JsonObject): PenicillinModel {
        val durationMillis by int("duration_millis")
        val aspectRatio by intList
        val variants by modelList<Variant>()

        data class Variant(override val json: JsonObject): PenicillinModel {
            val bitrate by nullableInt
            val contentType by string("content_type")
            val url by string
        }
    }
}

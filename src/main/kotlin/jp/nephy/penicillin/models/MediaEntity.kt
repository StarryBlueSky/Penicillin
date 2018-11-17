@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.models.special.StatusID
import kotlinx.serialization.json.long

data class MediaEntity(override val json: JsonObject): PenicillinModel {
    val additionalMediaInfo by model<AdditionalMediaInfo?>(key = "additional_media_info")
    val displayUrl by string("display_url")
    val expandedUrl by string("expanded_url")
    val extAltText by nullableString("ext_alt_text")
    val features by model<MediaFeature>()
    val id by long
    val idStr by string("id_str")
    val indices by intList
    val mediaUrl by string("media_url")
    val mediaUrlHttps by string("media_url_https")
    val sizes by model<Photo>()
    val sourceStatusId by nullableLong("source_status_id")
    val sourceStatusIdObj by nullableLambda("source_status_id") { StatusID(it.long) }
    val sourceStatusIdStr by nullableString("source_status_id_str")
    val type by string
    val url by string
    val videoInfo by model<VideoInfo?>(key = "video_info")
}

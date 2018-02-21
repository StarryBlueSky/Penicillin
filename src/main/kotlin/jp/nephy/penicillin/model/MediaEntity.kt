package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.misc.StatusID

@Suppress("UNUSED")
class MediaEntity(override val json: JsonObject): JsonModel {
    val additionalMediaInfo by json.byModel<AdditionalMediaInfo?>(key = "additional_media_info")
    val displayUrl by json.byString("display_url")
    val expandedUrl by json.byUrl("expanded_url")
    val extAltText by json.byNullableString("ext_alt_text")
    val features by json.byModel<MediaFeature>()
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byIntList
    val mediaUrl by json.byUrl
    val mediaUrlHttps by json.byUrl
    val sizes by json.byModel<Photo>()
    val sourceStatusId by json.byLambda("source_status_id") { StatusID(long) }
    val sourceStatusIdStr by json.byNullableString("source_status_id_str")
    val type by json.byString
    val url by json.byUrl
    val videoInfo by json.byModel<VideoInfo?>(key = "video_info")
}

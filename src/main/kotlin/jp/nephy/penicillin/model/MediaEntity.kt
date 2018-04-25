package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.model.special.StatusID


class MediaEntity(override val json: JsonObject): JsonModel {
    val additionalMediaInfo by json.byModel<AdditionalMediaInfo?>(key = "additional_media_info")
    val displayUrl by json.byString("display_url")
    val expandedUrl by json.byString("expanded_url")
    val extAltText by json.byNullableString("ext_alt_text")
    val features by json.byModel<MediaFeature>()
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byIntList
    val mediaUrl by json.byString("media_url")
    val mediaUrlHttps by json.byString("media_url_https")
    val sizes by json.byModel<Photo>()
    val sourceStatusId by json.byLambda("source_status_id") { StatusID(long) }
    val sourceStatusIdStr by json.byNullableString("source_status_id_str")
    val type by json.byString
    val url by json.byString
    val videoInfo by json.byModel<VideoInfo?>(key = "video_info")
}

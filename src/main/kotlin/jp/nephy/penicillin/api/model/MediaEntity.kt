package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.*

class MediaEntity(val json: JsonElement) {
    val additionalMediaInfo by json.byNullableAdditionalMediaInfo("additional_media_info")
    val displayUrl by json.byString("display_url")
    val expandedUrl by json.byURL("expanded_url")
    val extAltText by json.byNullableString("ext_alt_text")
    val features by json.byMediaFeature
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byIntArray
    val mediaUrl by json.byURL
    val mediaUrlHttps by json.byURL
    val sizes by json.byPhoto
    val sourceStatusId by json.byNullableStatusID("source_status_id")
    val sourceStatusIdStr by json.byNullableString("source_status_id_str")
    val type by json.byString
    val url by json.byURL
    val videoInfo by json.byNullableVideoInfo("video_info")
}

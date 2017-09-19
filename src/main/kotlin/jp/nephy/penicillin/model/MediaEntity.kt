package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.StatusID
import java.net.URL

class MediaEntity(val json: JsonElement) {
    val additionalMediaInfo by json.byModel<AdditionalMediaInfo?>("additional_media_info")
    val displayUrl by json.byString("display_url")
    val expandedUrl by json.byConverter<String, URL>("expanded_url")
    val extAltText by json.byNullableString("ext_alt_text")
    val features by json.byModel<MediaFeature>()
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byList<Int>()
    val mediaUrl by json.byConverter<String, URL>()
    val mediaUrlHttps by json.byConverter<String, URL>()
    val sizes by json.byModel<Photo>()
    val sourceStatusId by json.byConverter<Long, StatusID?>("source_status_id")
    val sourceStatusIdStr by json.byNullableString("source_status_id_str")
    val type by json.byString
    val url by json.byConverter<String, URL>()
    val videoInfo by json.byModel<VideoInfo?>("video_info")
}

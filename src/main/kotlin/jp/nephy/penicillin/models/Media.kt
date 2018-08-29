package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


data class Media(override val json: JsonObject): PenicillinModel {
    val expiresAfterSecs by json.byNullableInt("expires_after_secs")
    val mediaId by json.byLong("media_id")
    val mediaIdString by json.byString("media_id_string")
    val mediaKey by json.byNullableString("media_key")
    val processingInfo by json.byModel<MediaProcessingInfo>(key = "processing_info")
    val size by json.byNullableInt
    val image by json.byModel<Image?>()
    val video by json.byModel<Video?>()
}

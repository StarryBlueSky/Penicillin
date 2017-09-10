package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byMediaProcessingInfo
import jp.nephy.penicillin.api.byNullableImage
import jp.nephy.penicillin.api.byNullableVideo

class Media(val json: JsonElement) {
    val expiresAfterSecs by json.byNullableInt("expires_after_secs")
    val mediaId by json.byLong("media_id")
    val mediaIdString by json.byString("media_id_string")
    val mediaKey by json.byNullableString("media_key")
    val processingInfo by json.byMediaProcessingInfo("processing_info")
    val size by json.byNullableInt
    val image by json.byNullableImage
    val video by json.byNullableVideo
}

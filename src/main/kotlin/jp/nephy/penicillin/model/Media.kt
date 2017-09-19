package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class Media(val json: JsonElement) {
    val expiresAfterSecs by json.byNullableInt("expires_after_secs")
    val mediaId by json.byLong("media_id")
    val mediaIdString by json.byString("media_id_string")
    val mediaKey by json.byNullableString("media_key")
    val processingInfo by json.byModel<MediaProcessingInfo>("processing_info")
    val size by json.byNullableInt
    val image by json.byModel<Image?>()
    val video by json.byModel<Video?>()
}

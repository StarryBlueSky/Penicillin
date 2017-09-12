package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byMediaProcessingInfo
import jp.nephy.penicillin.converter.byNullableImage
import jp.nephy.penicillin.converter.byNullableVideo

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

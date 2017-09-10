package jp.nephy.penicillin.api.result

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableLong
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class MediaUpdateStatus(val json: JsonElement) {
    val expiresAfterSecs by json.byNullableInt("expires_after_secs") // 3595
    val mediaId by json.byNullableLong("media_id") // 710511363345354753
    val mediaIdString by json.byNullableString("media_id_string") // "710511363345354753"
    val mediaKey by json.byNullableString("media_key") // "7_710511363345354753"
    val processingInfo by json.byNullableObject("processing_info") // {"state": "in_progress", "check_after_secs": 10, "progress_percent": 8, "error": {"code": 1, "name": "InvalidMedia", "message": "Unsupported video format"}}
    val video by json.byNullableObject // {"video_type": "video/mp4"}
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Media(override val json: JsonObject): PenicillinModel {
    val expiresAfterSecs by nullableInt("expires_after_secs")
    val mediaId by long("media_id")
    val mediaIdString by string("media_id_string")
    val mediaKey by nullableString("media_key")
    val processingInfo by model<MediaProcessingInfo>(key = "processing_info")
    val size by nullableInt
    val image by model<Image?>()
    val video by model<Video?>()
}

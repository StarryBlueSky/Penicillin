package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

data class CoverMedia(override val json: JsonObject): CommonCoverMedia(json)

abstract class CommonCoverMedia(json: JsonObject): PenicillinModel {
    val tweetId by json.byString("tweet_id")
    val type by json.byString

    val mediaId by json["media"].byString("media_id")
    val mediaUrl by json["media"].byUrl("url")
    val mediaWidth by json["media"]["size"].byInt("w")
    val mediaHeight by json["media"]["size"].byInt("h")

    val renderCropSquare by json["render"]["crops"].byModel<FaceCoordinate>(key = "square")
    val renderCropPortrait9to16 by json["render"]["crops"].byModel<FaceCoordinate>(key = "portrait_9_16")
    val renderCropPortrait3to4 by json["render"]["crops"].byModel<FaceCoordinate>(key = "portrait_3_4")
    val renderCropPortrait16to9 by json["render"]["crops"].byModel<FaceCoordinate>(key = "portrait_16_9")
}

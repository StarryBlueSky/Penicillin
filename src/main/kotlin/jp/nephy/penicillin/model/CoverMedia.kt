package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byModel
import java.net.URL

open class CoverMedia(val json: JsonElement) {
    val tweetId by json.byString("tweet_id")
    val type by json.byString

    val mediaId by json["media"].byString("media_id")
    val mediaUrl by json["media"].byConverter<String, URL>("url")
    val mediaWidth by json["media"]["size"].byInt("w")
    val mediaHeight by json["media"]["size"].byInt("h")

    val renderCropSquare by json["render"]["crops"].byModel<FaceCoordinate>("square")
    val renderCropPortrait9_16 by json["render"]["crops"].byModel<FaceCoordinate>("portrait_9_16")
    val renderCropPortrait3_4 by json["render"]["crops"].byModel<FaceCoordinate>("portrait_3_4")
    val renderCropPortrait16_9 by json["render"]["crops"].byModel<FaceCoordinate>("portrait_16_9")
}
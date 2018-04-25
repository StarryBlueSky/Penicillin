package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.model.special.CreatedAt


class Moment(override val json: JsonObject): JsonModel {
    val id by json["moment"].byString
    val title by json["moment"].byString
    val description by json["moment"].byString
    val url by json["moment"].byUrl
    val isLive by json["moment"].byBool("is_live")
    val time by json["moment"].byString("time_string")
    val lastPublishTime by json["moment"].byLambda("last_publish_time") { CreatedAt(string) }
    val subcategory by json["moment"].byString("subcategory_string")
    val sensitive by json["moment"].byBool
    val duration by json["moment"].byString("duration_string")
    val canSubscribe by json["moment"].byBool("can_subscribe")
    val capsuleContentsVersion by json["moment"].byString("capsule_contents_version")
    val totalLikes by json["moment"].byInt("total_likes")
    val users by json.byLambda { json["moment"]["users"].asJsonObject.toMap().values.map { User(it.jsonObject) } }
    val coverMedia by json["moment"].byModel<CoverMedia>(key = "cover_media")

    val displayStyle by json.byString("display_style")
    val momentPosition by json["context"]["context_scribe_info"].byString("moment_position")
    val tweets by json.byLambda { asJsonObject.toMap().values.map { Status(it.jsonObject) } }

    val coverFormat by json.byModel<CoverFormat>(key = "cover_format")
    val largeFormat by json.byModel<CoverFormat>(key = "large_format")
    val thumbnailFormat by json.byModel<CoverFormat>(key = "thumbnail_format")
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.CreatedAt
import java.net.URL

@Suppress("UNUSED")
class Moment(val json: JsonElement) {
    val id by json["moment"].byString
    val title by json["moment"].byString
    val description by json["moment"].byString
    val url by json["moment"].byConverter<String, URL>()
    val isLive by json["moment"].byBool("is_live")
    val time by json["moment"].byString("time_string")
    val lastPublishTime by json["moment"].byConverter<String, CreatedAt>("last_publish_time")
    val subcategory by json["moment"].byString("subcategory_string")
    val sensitive by json["moment"].byBool
    val duration by json["moment"].byString("duration_string")
    val canSubscribe by json["moment"].byBool("can_subscribe")
    val capsuleContentsVersion by json["moment"].byString("capsule_contents_version")
    val totalLikes by json["moment"].byInt("total_likes")
    val users = json["moment"]["users"].asJsonObject.toMap().values.map { User(it) }
    val coverMedia by json["moment"].byModel<CoverMedia>("cover_media")

    val displayStyle by json.byString("display_style")
    val momentPosition by json["context"]["context_scribe_info"].byString("moment_position")
    val tweets = json["tweets"].asJsonObject.toMap().values.map { Status(it) }

    val coverFormat by json.byModel<CoverFormat>("cover_format")
    val largeFormat by json.byModel<CoverFormat>("large_format")
    val thumbnailFormat by json.byModel<CoverFormat>("thumbnail_format")
}

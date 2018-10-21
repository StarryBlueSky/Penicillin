@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.immutableJsonObject
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt

data class Moment(override val json: ImmutableJsonObject): PenicillinModel {
    private val moment by immutableJsonObject
    val id by moment.byString
    val title by moment.byString
    val description by moment.byString
    val url by moment.byString
    val isLive by moment.byBoolean("is_live")
    val time by moment.byString("time_string")
    val lastPublishTime by moment.byLambda("last_publish_time") { CreatedAt(it.string) }
    val subcategory by moment.byString("subcategory_string")
    val sensitive by moment.byBoolean
    val duration by moment.byString("duration_string")
    val canSubscribe by moment.byBoolean("can_subscribe")
    val capsuleContentsVersion by moment.byString("capsule_contents_version")
    val totalLikes by moment.byInt("total_likes")

    val users by moment.byLambda { it.immutableJsonObject.toMap().values.map { json -> User(json.immutableJsonObject) } }
    val coverMedia by moment.byModel<CoverMedia>(key = "cover_media")

    val displayStyle by string("display_style")

    private val context by immutableJsonObject
    private val contectScribeInfo by context.byImmutableJsonObject
    val momentPosition by contectScribeInfo.byString("moment_position")
    val tweets by lambda { it.immutableJsonObject.toMap().values.map { json -> Status(json.immutableJsonObject) } }

    val coverFormat by model<CoverFormat>(key = "cover_format")
    val largeFormat by model<CoverFormat>(key = "large_format")
    val thumbnailFormat by model<CoverFormat>(key = "thumbnail_format")
}

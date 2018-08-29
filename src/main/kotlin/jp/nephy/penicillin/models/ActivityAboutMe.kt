package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


data class ActivityAboutMe(override val json: JsonObject): PenicillinModel {
    val activityEvents by json.byModelList<ActivityEvent>()
    val genericActivities by json.byJsonObject("generic_activities")
    val pagination by json.byJsonObject

    val maxCursor by json["pagination"].byString("max_cursor")
    val minCursor by json["pagination"].byString("min_cursor")
    val gaps by json["pagination"].byJsonObject
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


class ActivityAboutMe(override val json: JsonObject): JsonModel {
    val activityEvents by json.byModelList<ActivityEvent>()
    val genericActivities by json.byJsonObject("generic_activities")
    val pagination by json.byJsonObject

    val maxCursor by json["pagination"].byString("max_cursor")
    val minCursor by json["pagination"].byString("min_cursor")
    val gaps by json["pagination"].byJsonObject
}

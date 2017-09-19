package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byObject
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class ActivityAboutMe(val json: JsonElement) {
    val activityEvents by json.byList<ActivityEvent>()
    val genericActivities by json.byObject("generic_activities")
    val pagination by json.byObject

    val maxCursor by json["pagination"].byString("max_cursor")
    val minCursor by json["pagination"].byString("min_cursor")
    val gaps by json["pagination"].byObject
}

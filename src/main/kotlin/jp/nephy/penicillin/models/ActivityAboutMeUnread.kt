package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong


data class ActivityAboutMeUnread(override val json: JsonObject): PenicillinModel {
    val cursor by json.byLong
}

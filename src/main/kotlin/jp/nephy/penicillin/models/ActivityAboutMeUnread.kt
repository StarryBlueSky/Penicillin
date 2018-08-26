package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong


class ActivityAboutMeUnread(override val json: JsonObject): PenicillinModel {
    val cursor by json.byLong
}

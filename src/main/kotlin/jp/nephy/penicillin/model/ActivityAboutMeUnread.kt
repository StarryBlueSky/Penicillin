package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLong


class ActivityAboutMeUnread(override val json: JsonObject): JsonModel {
    val cursor by json.byLong
}

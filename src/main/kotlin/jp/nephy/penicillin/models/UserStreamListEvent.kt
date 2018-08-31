package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel

data class UserStreamListEvent(override val json: JsonObject): UserStreamEvent(json) {
    val targetObject by json.byModel<TwitterList>(key = "target_object")
}

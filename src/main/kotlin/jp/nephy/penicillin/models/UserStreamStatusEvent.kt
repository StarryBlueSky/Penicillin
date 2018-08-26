package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


class UserStreamStatusEvent(json: JsonObject): UserStreamEvent(json) {
    val targetObject by json.byModel<Status>(key = "target_object")
}

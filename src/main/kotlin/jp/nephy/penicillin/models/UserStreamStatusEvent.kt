package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model

data class UserStreamStatusEvent(override val json: JsonObject): UserStreamEvent() {
    val targetObject by model<Status>(key = "target_object")
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model

data class UserStreamListEvent(override val json: JsonObject): UserStreamEvent() {
    val targetObject by model<TwitterList>(key = "target_object")
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model

data class UserStreamStatusEvent(val parentJson: JsonObject): UserStreamEvent(parentJson) {
    val targetObject by model<Status>(key = "target_object")
}

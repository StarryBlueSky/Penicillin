@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model

data class UserStreamListEvent(val parentJson: JsonObject): UserStreamEvent(parentJson) {
    val targetObject by model<TwitterList>(key = "target_object")
}

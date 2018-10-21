@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.model

data class UserStreamListEvent(val parentJson: ImmutableJsonObject): UserStreamEvent(parentJson) {
    val targetObject by model<TwitterList>(key = "target_object")
}

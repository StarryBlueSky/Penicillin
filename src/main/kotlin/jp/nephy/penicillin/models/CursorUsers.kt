@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.modelList

data class CursorUsers(val parentJson: ImmutableJsonObject): PenicillinCursorModel(parentJson) {
    val users by modelList<User>()
}

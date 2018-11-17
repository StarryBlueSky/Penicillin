@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.modelList

data class CursorUsers(val parentJson: JsonObject): PenicillinCursorModel(parentJson) {
    val users by modelList<User>()
}

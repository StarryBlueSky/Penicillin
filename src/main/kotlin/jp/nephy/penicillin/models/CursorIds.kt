@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.longList

data class CursorIds(val parentJson: JsonObject): PenicillinCursorModel(parentJson) {
    val ids by longList
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.longList

data class CursorIds(val parentJson: ImmutableJsonObject): PenicillinCursorModel(parentJson) {
    val ids by longList
}

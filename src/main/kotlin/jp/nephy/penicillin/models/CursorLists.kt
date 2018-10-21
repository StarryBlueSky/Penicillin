@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.modelList

data class CursorLists(val parentJson: ImmutableJsonObject): PenicillinCursorModel(parentJson) {
    val lists by modelList<TwitterList>()
}

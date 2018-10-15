package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.modelList

data class CursorLists(override val json: JsonObject): PenicillinCursorModel() {
    val lists by modelList<TwitterList>()
}

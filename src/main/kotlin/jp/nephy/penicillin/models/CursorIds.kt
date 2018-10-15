package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.longList

data class CursorIds(override val json: JsonObject): PenicillinCursorModel() {
    val ids by longList
}

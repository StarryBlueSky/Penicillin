package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.modelList

data class CursorUsers(override val json: JsonObject): PenicillinCursorModel() {
    val users by modelList<User>()
}

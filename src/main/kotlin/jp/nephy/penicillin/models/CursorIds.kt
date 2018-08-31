package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList

data class CursorIds(override val json: JsonObject): PenicillinCursorModel(json) {
    val ids by json.byLongList
}

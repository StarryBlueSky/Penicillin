package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList

@Suppress("UNUSED")
class CursorIds(json: JsonObject): Cursor(json) {
    val ids by json.byLongList
}

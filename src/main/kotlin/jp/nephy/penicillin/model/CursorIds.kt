package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList


class CursorIds(json: JsonObject): Cursor(json) {
    val ids by json.byLongList
}

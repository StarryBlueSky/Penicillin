package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
open class Cursor(final override val json: JsonObject): JsonModel {
    val nextCursor by json.byLong("next_cursor")
    val nextCursorStr by json.byString("next_cursor_str")
    val previousCursor by json.byLong("previous_cursor")
    val previousCursorStr by json.byString("previous_cursor_str")
}

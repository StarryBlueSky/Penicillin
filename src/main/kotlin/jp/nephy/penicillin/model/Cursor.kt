package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
open class Cursor(val json: JsonElement) {
    val nextCursor by json.byLong("next_cursor")
    val nextCursorStr by json.byString("next_cursor_str")
    val previousCursor by json.byLong("previous_cursor")
    val previousCursorStr by json.byString("previous_cursor_str")
}

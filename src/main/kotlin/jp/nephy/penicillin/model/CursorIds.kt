package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class CursorIds(json: JsonElement): Cursor(json) {
    val ids by json.byList<Long>()
}

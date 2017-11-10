package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class CursorLists(json: JsonElement): Cursor(json) {
    val lists by json.byList<List>()
}

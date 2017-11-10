package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class CursorUsers(json: JsonElement): Cursor(json) {
    val users by json.byList<User>()
}

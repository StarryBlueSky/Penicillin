package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList

@Suppress("UNUSED")
class CursorUsers(json: JsonObject): Cursor(json) {
    val users by json.byModelList<User>()
}

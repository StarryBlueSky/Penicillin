package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList


class CursorUsers(json: JsonObject): Cursor(json) {
    val users by json.byModelList<User>()
}

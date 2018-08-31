package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList

data class CursorUsers(override val json: JsonObject): PenicillinCursorModel(json) {
    val users by json.byModelList<User>()
}
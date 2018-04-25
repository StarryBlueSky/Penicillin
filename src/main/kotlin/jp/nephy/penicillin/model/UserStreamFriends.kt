package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLongList

class UserStreamFriends(override val json: JsonObject): JsonModel {
    val friends by json.byLongList()
}

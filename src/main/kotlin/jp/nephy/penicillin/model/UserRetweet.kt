package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString

class UserRetweet(override val json: JsonObject): JsonModel {
    val id by json.byLong
    val idStr by json.byString("id_str")
}

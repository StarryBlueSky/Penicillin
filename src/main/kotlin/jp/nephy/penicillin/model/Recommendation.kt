package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString

class Recommendation(override val json: JsonObject): JsonModel {
    val token by json.byString
    val userId by json.byString("user_id")
    val user by json.byModel<User>()
}

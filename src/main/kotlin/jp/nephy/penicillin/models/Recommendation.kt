package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString

data class Recommendation(override val json: JsonObject): PenicillinModel {
    val token by json.byString
    val userId by json.byString("user_id")
    val user by json.byModel<User>()
}

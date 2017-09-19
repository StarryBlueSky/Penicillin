package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class Recommendation(val json: JsonElement) {
    val token by json.byString
    val userId by json.byString("user_id")
    val user by json.byModel<User>()
}

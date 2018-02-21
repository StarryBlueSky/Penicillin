package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class VerifyCredentials(json: JsonObject): User(json) {
    val email by json.byString
    val phone by json.byModel<Phone>()
}

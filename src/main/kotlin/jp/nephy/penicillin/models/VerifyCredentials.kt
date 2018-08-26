package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString


class VerifyCredentials(json: JsonObject): User(json) {
    val email by json.byString
    val phone by json.byModel<Phone>()
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byPhone

class VerifyCredentials(json: JsonElement): User(json) {
    val email by json.byString
    val phone by json.byPhone
}

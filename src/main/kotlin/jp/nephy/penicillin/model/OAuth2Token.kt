package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class OAuth2Token(val json: JsonElement) {
    val tokenType by json.byNullableString("token_type")
    val token by json.byString("access_token")
}

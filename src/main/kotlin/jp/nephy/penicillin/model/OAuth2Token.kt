package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class OAuth2Token(override val json: JsonObject): JsonModel {
    val tokenType by json.byNullableString("token_type")
    val token by json.byString("access_token")
}

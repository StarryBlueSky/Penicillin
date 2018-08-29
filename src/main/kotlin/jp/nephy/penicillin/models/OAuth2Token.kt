package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString


data class OAuth2Token(override val json: JsonObject): PenicillinModel {
    val tokenType by json.byNullableString("token_type")
    val token by json.byString("access_token")
}

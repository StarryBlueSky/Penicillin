@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class OAuth2Token(override val json: ImmutableJsonObject): PenicillinModel {
    val tokenType by nullableString("token_type")
    val token by string("access_token")
}

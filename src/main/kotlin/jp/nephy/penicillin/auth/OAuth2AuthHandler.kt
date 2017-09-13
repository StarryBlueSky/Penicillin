package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.BearerToken

class OAuth2AuthHandler(private val token: BearerToken) {
    fun sign() = "Bearer $token"
}

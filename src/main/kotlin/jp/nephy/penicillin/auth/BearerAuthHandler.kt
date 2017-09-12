package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.BearerToken

class BearerAuthHandler(private val token: BearerToken) {
    fun sign() = "Bearer $token"
}

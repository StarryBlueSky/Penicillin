package jp.nephy.penicillin.core.auth

import io.ktor.http.HttpMethod
import io.ktor.http.encodeOAuth
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuthUtil private constructor() {
    companion object {
        fun randomUUID(): String {
            return UUID.randomUUID().toString().toUpperCase()
        }

        fun currentEpochTime(): String {
            return "${Date().time / 1000}"
        }

        fun signatureParamString(param: Map<String, String>): String {
            return param.toList().joinToString("&") { "${it.first}=${it.second}" }.encodeOAuth()
        }

        fun signingBaseString(httpMethod: HttpMethod, url: String, signatureParamString: String): String {
            return "${httpMethod.value}&${url.split("?").first().encodeOAuth()}&$signatureParamString"
        }

        fun signingKey(consumerSecret: String, accessTokenSecret: String?): SecretKeySpec {
            return SecretKeySpec("${consumerSecret.encodeOAuth()}&${accessTokenSecret.orEmpty().encodeOAuth()}".toByteArray(), "HmacSHA1")
        }

        fun signature(signingKey: SecretKeySpec, signatureBaseString: String): String {
            return Mac.getInstance(signingKey.algorithm).apply {
                init(signingKey)
            }.doFinal(signatureBaseString.toByteArray()).let {
                Base64.getEncoder().encodeToString(it)
            }.encodeOAuth()
        }
    }
}

package jp.nephy.penicillin.core.auth

import io.ktor.http.HttpMethod
import io.ktor.http.encodeOAuth
import io.ktor.util.InternalAPI
import io.ktor.util.date.GMTDate
import io.ktor.util.encodeBase64
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object OAuthUtil {
    fun randomUUID(): String {
        return UUID.randomUUID().toString().toUpperCase()
    }

    fun currentEpochTime(): String {
        return "${GMTDate().timestamp / 1000}"
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
    
    @UseExperimental(InternalAPI::class)
    fun signature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).let {
            encodeBase64(it)
        }.encodeOAuth()
    }
}

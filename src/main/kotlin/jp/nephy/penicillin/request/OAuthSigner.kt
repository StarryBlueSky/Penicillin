package jp.nephy.penicillin.request

import jp.nephy.penicillin.Session
import jp.nephy.penicillin.toBase64Encode
import jp.nephy.penicillin.toURLEncode
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


enum class AuthorizationType {
    OAuth1a, OAuth2, OAuth2RequestToken, None
}

class OAuthSigner(private val session: Session, private val requestBuilder: PenicillinRequestBuilder) {
    companion object {
        fun getRandomUUID(): String {
            return UUID.randomUUID().toString().toUpperCase()
        }

        fun getB3TraceId(n: Int = 16): String {
            val seed = SecureRandom()
            return buildString {
                repeat(n) {
                    append(Integer.toHexString(seed.nextInt(16)))
                }
            }
        }
    }

    fun sign(): String? {
        return when (requestBuilder.authorizationType) {
            AuthorizationType.OAuth1a -> {
                val authorizationHeaderComponent = linkedMapOf(
                        "oauth_signature" to null, "oauth_callback" to if (session.accessToken == null) {
                    (requestBuilder.callbackUrl ?: "oob")
                } else {
                    null
                },
                        "oauth_nonce" to getRandomUUID(), "oauth_timestamp" to getCurrentEpochTime(),
                        "oauth_consumer_key" to session.consumerKey !!, "oauth_token" to session.accessToken,
                        "oauth_version" to "1.0", "oauth_signature_method" to "HMAC-SHA1"
                )

                val signatureBaseString = getSigningBaseString(authorizationHeaderComponent)
                authorizationHeaderComponent["oauth_signature"] = getSignature(getSigningKey(), signatureBaseString)

                "OAuth ${authorizationHeaderComponent.filterValues { it != null }.toList().joinToString(", ") { "${it.first}=\"${it.second}\"" }}"
            }
            AuthorizationType.OAuth2 -> {
                "Bearer ${session.bearerToken !!}"
            }
            AuthorizationType.OAuth2RequestToken -> {
                val encoded = "${session.consumerKey !!.toURLEncode()}:${session.consumerSecret !!.toURLEncode()}".toBase64Encode()
                return "Basic $encoded"
            }
            AuthorizationType.None -> null
        }
    }

    private fun getCurrentEpochTime(): String {
        return "${Date().time / 1000}"
    }

    private fun getSigningBaseString(authorizationHeaderComponent: LinkedHashMap<String, String?>): String {
        val signatureParam = sortedMapOf<String, String>().apply {
            authorizationHeaderComponent.filterValues { it != null }.forEach {
                put(it.key, it.value)
            }
            if (! requestBuilder.isFileData) {
                (requestBuilder.queries + requestBuilder.data).forEach({
                    put(it.key.toURLEncode(), it.value.toURLEncode())
                })
            }
        }

        val signatureParamString = signatureParam.toList().joinToString("&") { "${it.first}=${it.second}" }.toURLEncode()

        return "${requestBuilder.httpMethod.name}&${requestBuilder.url.toURLEncode()}&$signatureParamString"
    }

    private fun getSigningKey(): SecretKeySpec {
        return SecretKeySpec("${session.consumerSecret !!.toURLEncode()}&${session.accessTokenSecret.orEmpty().toURLEncode()}".toByteArray(), "HmacSHA1")
    }

    private fun getSignature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).toBase64Encode().toURLEncode()
    }
}

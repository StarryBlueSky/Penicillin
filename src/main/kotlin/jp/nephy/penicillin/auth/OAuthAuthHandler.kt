package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.AccessToken
import jp.nephy.penicillin.credential.AccessTokenSecret
import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.Util
import jp.nephy.penicillin.misc.toBase64Encode
import jp.nephy.penicillin.misc.toURLEncode
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuthAuthHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret, private val at: AccessToken, private val ats: AccessTokenSecret) {
    fun sign(method: HTTPMethod, url: String, data: List<Pair<String,String>>?=null, excludeData: Boolean=false): String {
        val authorizationHeaderComponent = linkedMapOf<String,String>().apply {
            put("oauth_signature", "")
            put("oauth_nonce", Util.getRandomUUID())
            put("oauth_timestamp", Util.getCurrentEpochTime().toString())
            put("oauth_consumer_key", ck.toString())
            put("oauth_token", at.toString())
            put("oauth_version", "1.0")
            put("oauth_signature_method", "HMAC-SHA1")
        }

        val signatureBaseString = getSigningBaseString(method, url, authorizationHeaderComponent, data, excludeData)
        val signingKey = getSigningKey(cs, ats)
        authorizationHeaderComponent["oauth_signature"] = getSignature(signingKey, signatureBaseString)

        val headerString = authorizationHeaderComponent.map {
            "${it.key}=\"${it.value}\""
        }.joinToString(", ")

        return "OAuth $headerString"
    }

    private fun getSigningBaseString(method: HTTPMethod, url: String, authorizationHeaderComponent: LinkedHashMap<String,String>, data: List<Pair<String,String>>?=null, excludeData: Boolean): String {
        val signatureParam = sortedMapOf<String, String>().apply {
            authorizationHeaderComponent.filterValues { it != "" }.forEach{
                put(it.key, it.value)
            }
            if (!excludeData) {
                data?.forEach({
                    put(it.first.toURLEncode(), it.second.toURLEncode())
                })
            }
        }
        val signatureParamString = signatureParam.map {
            "${it.key}=${it.value}"
        }.joinToString("&").toURLEncode()

        return "$method&${url.toString().toURLEncode()}&$signatureParamString"
    }

    private fun getSigningKey(cs: ConsumerSecret, ats: AccessTokenSecret) = SecretKeySpec("${cs.toString().toURLEncode()}&${ats.toString().toURLEncode()}".toByteArray(), "HmacSHA1")

    private fun getSignature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).toBase64Encode().toURLEncode()
    }
}

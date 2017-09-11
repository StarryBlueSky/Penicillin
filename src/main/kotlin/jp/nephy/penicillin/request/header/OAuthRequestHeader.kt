package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.*
import jp.nephy.penicillin.request.credential.AccessToken
import jp.nephy.penicillin.request.credential.AccessTokenSecret
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import java.net.URL
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuthRequestHeader(private val method: HTTPMethod, private val url: URL, private val uuid: String, deviceId: String): AbsRequestHeader() {
    init {
        builder.apply {
            add("Host", url.host)
            add("Authorization", "")
            add("X-Twitter-Client-Version", "6.59.3")
            add("Accept", "*/*")
            add("X-Client-UUID", uuid)
            add("X-Twitter-Client-Language", "ja")
            add("X-B3-TraceId", Util.getB3TraceId())
            add("Accept-Language", "ja")
            add("X-Twitter-Client-DeviceID", deviceId)
            add("User-Agent", "Twitter-iPhone/6.59.3 iOS/9.3.3 (Apple;iPhone8,2;;;;;1)")
            add("X-Twitter-Client-Limit-Ad-Tracking", "1")
            add("X-Twitter-API-Version", "5")
            add("X-Twitter-Client", "Twitter-iPhone")
        }
    }

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, data: Map<String,String>?=null, excludeData: Boolean=false): OAuthRequestHeader {
        val authorizationHeaderComponent = linkedMapOf<String,String>().apply {
            put("oauth_signature", "")
            put("oauth_nonce", uuid)
            put("oauth_timestamp", Util.getCurrentEpochTime().toString())
            put("oauth_consumer_key", ck.toString())
            put("oauth_token", at.toString())
            put("oauth_version", "1.0")
            put("oauth_signature_method", "HMAC-SHA1")
        }

        val signatureBaseString = getSigningBaseString(url, authorizationHeaderComponent, data, excludeData)
        val signingKey = getSigningKey(cs, ats)
        authorizationHeaderComponent["oauth_signature"] = getSignature(signingKey, signatureBaseString)

        val headerString = authorizationHeaderComponent.map {
            "${it.key}=\"${it.value}\""
        }.joinToString(", ")

        builder["Authorization"] = "OAuth $headerString"
        return this
    }

    private fun getSigningBaseString(url: URL, authorizationHeaderComponent: LinkedHashMap<String,String>, data: Map<String,String>?=null, excludeData: Boolean): String {
        val signatureParam = sortedMapOf<String, String>().apply {
            authorizationHeaderComponent.filterValues { it != "" }.forEach{
                put(it.key, it.value)
            }
            if (!excludeData) {
                data?.forEach({
                    put(it.key.toURLEncode(), it.value.toURLEncode())
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

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
        _header.apply {
            put("Host", url.host)
            put("Authorization", "")
            put("X-Twitter-Client-Version", "6.59.3")
            put("Accept", "*/*")
            put("X-Client-UUID", uuid)
            put("X-Twitter-Client-Language", "ja")
            put("X-B3-TraceId", Util.getB3TraceId())
            put("Accept-Language", "ja")
            put("Accept-Encoding", "gzip, deflate")
            put("X-Twitter-Client-DeviceID", deviceId)
            if (method === HTTPMethod.POST) {
                put("Content-Type", "application/x-www-form-urlencoded")
                put("Content-Length", "")
            }
            put("User-Agent", "Twitter-iPhone/6.59.3 iOS/9.3.3 (Apple;iPhone8,2;;;;;1)")
            put("X-Twitter-Client-Limit-Ad-Tracking", "1")
            put("X-Twitter-API-Version", "5")
            put("X-Twitter-Client", "Twitter-iPhone")
        }
    }

    fun multipart() {
        _header["Content-Type"] = "multipart/form-data; boundary=----${System.currentTimeMillis().toHexString()}"
    }

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, data: Map<String,String>?=null): OAuthRequestHeader {
        val authorizationHeaderComponent = linkedMapOf<String,String?>().apply {
            put("oauth_signature", null)
            put("oauth_nonce", uuid)
            put("oauth_timestamp", Util.getCurrentEpochTime().toString())
            put("oauth_consumer_key", ck.toString())
            put("oauth_token", at.toString())
            put("oauth_version", "1.0")
            put("oauth_signature_method", "HMAC-SHA1")
        }

        val signatureBaseString = getSigningBaseString(url, authorizationHeaderComponent, data)
        val signingKey = getSigningKey(cs, ats)
        authorizationHeaderComponent["oauth_signature"] = getSignature(signingKey, signatureBaseString)

        val headerString = authorizationHeaderComponent.map {
            "${it.key}=\"${it.value}\""
        }.joinToString(", ")

        _header["Authorization"] = "OAuth $headerString"
        return this
    }

    private fun getSigningBaseString(url: URL, authorizationHeaderComponent: LinkedHashMap<String,String?>, data: Map<String,String>?=null): String {
        val signatureParam = sortedMapOf<String, String>().apply {
            authorizationHeaderComponent.filterValues { it != null }.forEach{
                put(it.key, it.value)
            }
            if ((method == HTTPMethod.POST && _header["Content-Type"] == "application/x-www-form-urlencoded") || method != HTTPMethod.POST) {
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

    private fun getSigningKey(cs: ConsumerSecret, ats: AccessTokenSecret): SecretKeySpec {
        return SecretKeySpec("${cs.toString().toURLEncode()}&${ats.toString().toURLEncode()}".toByteArray(), "HmacSHA1")
    }

    private fun getSignature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).toBase64Encode().toURLEncode()
    }
}

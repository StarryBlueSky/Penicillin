package jp.nephy.penicillin

import java.net.URL
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

open class RequestHeaderBase {
    protected val _header: MutableMap<String,String> = mutableMapOf()

    fun get(): MutableMap<String,String> {
        return _header
    }

    fun setLength(data: Map<String,String>?=null) {
        if (data == null) {
            return
        }
        _header["Content-Length"] = data.toParamString().length.toString()
    }
}

class OAuthRequestHeader(private val method: HTTPMethod, private val url: URL, private val uuid: String, deviceId: String): RequestHeaderBase() {
    init {
        _header.apply {
            put("Host", url.host)
            put("Authorization", "")
            put("X-Twitter-Client-Version", "6.59.3")
            put("Accept", "*/*")
            put("X-Client-UUID", uuid)
            put("X-Twitter-Client-Language", "ja")
            put("X-B3-TraceId", Common.getB3TraceId())
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

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, data: Map<String,String>?=null): OAuthRequestHeader {
        val authorizationHeaderComponent = linkedMapOf<String,String?>().apply {
            put("oauth_signature", null)
            put("oauth_nonce", uuid)
            put("oauth_timestamp", Common.getCurrentEpochTime().toString())
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
            data?.forEach({
                put(it.key.toURLEncode(), it.value.toURLEncode())
            })
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
        println(signatureBaseString)
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).toBase64Encode().toURLEncode()
    }
}

class BasicRequestHeader(url: URL): RequestHeaderBase() {
    init {
        _header.apply {
            put("Host", url.host)
            put("X-B3-TraceId", Common.getB3TraceId())
            put("X-Twitter-Client-Language", "ja")
            put("Accept", "*/*")
            put("Accept-Language", "ja")
            put("Authorization", "")
            put("Accept-Encoding", "gzip, deflate")
            put("User-Agent", "Twitter/7.5.1 CFNetwork/758.5.3 Darwin/15.6.0")
        }
    }

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret): BasicRequestHeader {
        val encoded: String = "$ck:$cs".toBase64Encode()

        _header["Authorization"] = "Basic $encoded"
        return this
    }
}

class BearerRequestHeader(url: URL): RequestHeaderBase() {
    init {
        _header.apply {
            put("Host", url.host)
            put("X-B3-TraceId", Common.getB3TraceId())
            put("X-Twitter-Client-Language", "ja")
            put("Accept", "*/*")
            put("Accept-Language", "ja")
            put("Authorization", "")
            put("Accept-Encoding", "gzip, deflate")
            put("User-Agent", "Twitter/7.5.1 CFNetwork/758.5.3 Darwin/15.6.0")
        }
    }

    fun authenticate(token: BearerToken): BearerRequestHeader {
        _header["Authorization"] = "Bearer $token"
        return this
    }
}

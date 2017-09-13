package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.Util

class OAuthRequestTokenHandler(val ck: ConsumerKey, val cs: ConsumerSecret) {
    fun sign(method: HTTPMethod, url: String, data: List<Pair<String,String>>?=null, callbackUrl: String?=null): String {
        val authorizationHeaderComponent = linkedMapOf<String,String>().apply {
            put("oauth_signature", "")
            put("oauth_callback", callbackUrl ?: "oob")
            put("oauth_nonce", Util.getRandomUUID())
            put("oauth_timestamp", Util.getCurrentEpochTime().toString())
            put("oauth_consumer_key", ck.toString())
            put("oauth_version", "1.0")
            put("oauth_signature_method", "HMAC-SHA1")
        }

        val signatureBaseString = OAuthUtil.getSigningBaseString(method, url, authorizationHeaderComponent, data, false)
        val signingKey = OAuthUtil.getSigningKey(cs)
        authorizationHeaderComponent["oauth_signature"] = OAuthUtil.getSignature(signingKey, signatureBaseString)

        val headerString = authorizationHeaderComponent.map {
            "${it.key}=\"${it.value}\""
        }.joinToString(", ")

        return "OAuth $headerString"
    }
}

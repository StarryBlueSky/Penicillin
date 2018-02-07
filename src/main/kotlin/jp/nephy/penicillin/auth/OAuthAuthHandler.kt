package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.AccessToken
import jp.nephy.penicillin.credential.AccessTokenSecret
import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.Util

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

        val signatureBaseString = OAuthUtil.getSigningBaseString(method, url, authorizationHeaderComponent, data, excludeData)
        val signingKey = OAuthUtil.getSigningKey(cs, ats)
        authorizationHeaderComponent["oauth_signature"] = OAuthUtil.getSignature(signingKey, signatureBaseString)

        val headerString = authorizationHeaderComponent.toList().joinToString(", ") {
            "${it.first}=\"${it.second}\""
        }

        return "OAuth $headerString"
    }
}

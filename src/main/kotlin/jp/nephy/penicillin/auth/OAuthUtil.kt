package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.AccessTokenSecret
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.toBase64Encode
import jp.nephy.penicillin.misc.toURLEncode
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuthUtil {
    companion object {
        fun getSigningBaseString(method: HTTPMethod, url: String, authorizationHeaderComponent: LinkedHashMap<String,String>, data: List<Pair<String,String>>?=null, excludeData: Boolean): String {
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

            return "$method&${url.toURLEncode()}&$signatureParamString"
        }

        fun getSigningKey(cs: ConsumerSecret) = SecretKeySpec("${cs.toString().toURLEncode()}&".toByteArray(), "HmacSHA1")
        fun getSigningKey(cs: ConsumerSecret, ats: AccessTokenSecret) = SecretKeySpec("${cs.toString().toURLEncode()}&${ats.toString().toURLEncode()}".toByteArray(), "HmacSHA1")

        fun getSignature(signingKey: SecretKeySpec, signatureBaseString: String): String {
            return Mac.getInstance(signingKey.algorithm).apply {
                init(signingKey)
            }.doFinal(signatureBaseString.toByteArray()).toBase64Encode().toURLEncode()
        }
    }
}

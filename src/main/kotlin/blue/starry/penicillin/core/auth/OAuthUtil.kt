/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package blue.starry.penicillin.core.auth

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.*
import io.ktor.util.date.GMTDate
import io.ktor.util.flattenForEach
import blue.starry.penicillin.core.request.copy
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * OAuth cryptography utilities.
 */
object OAuthUtil {
    private const val macAlgorithm = "HmacSHA1"

    /**
     * Generates random uuid string with upper case.
     */
    val randomUUID: String
        get() = UUID.randomUUID().toString().toUpperCase()

    /**
     * Current epoch time string in seconds.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val currentEpochTime: String
        get() = "${GMTDate().timestamp / 1000}"

    /**
     * Creates initial authorization header components.
     */
    fun initialAuthorizationHeaderComponents(callback: String? = null, nonce: String = randomUUID, timestamp: String = currentEpochTime, consumerKey: String? = null, accessToken: String? = null): MutableMap<String, String?> {
        requireNotNull(consumerKey)
        
        return linkedMapOf(
            "oauth_signature" to null,
            "oauth_callback" to callback,
            "oauth_nonce" to nonce,
            "oauth_timestamp" to timestamp,
            "oauth_consumer_key" to consumerKey,
            "oauth_token" to accessToken,
            "oauth_version" to "1.0",
            "oauth_signature_method" to "HMAC-SHA1"
        )
    }

    /**
     * Creates signature param.
     */
    fun signatureParam(authorizationHeaderComponent: Map<String, String?>, body: Any, parameters: ParametersBuilder, forms: ParametersBuilder): Map<String, String> {
        return sortedMapOf<String, String>().also { map ->
            authorizationHeaderComponent.filterValues { it != null }.forEach {
                map[it.key.encodeURLParameter()] = it.value?.encodeURLParameter()
            }
            
            if (body !is MultiPartFormDataContent) {
                val params = parameters.copy().build() + forms.copy().build()
                
                params.flattenForEach { key, value ->
                    map[key.encodeURLParameter()] = value.encodeURLParameter()
                }
            }
        }
    }

    /**
     * Creates signature param string.
     */
    fun signatureParamString(param: Map<String, String>): String {
        return param.toList().joinToString("&") { "${it.first}=${it.second}" }.encodeOAuth()
    }

    /**
     * Creates signing base string.
     */
    fun signingBaseString(httpMethod: HttpMethod, url: Url, signatureParamString: String): String {
        return "${httpMethod.value.toUpperCase()}&${url.toString().split("?").first().encodeOAuth()}&$signatureParamString"
    }

    /**
     * Creates signing key.
     */
    fun signingKey(consumerSecret: String, accessTokenSecret: String? = null): SecretKeySpec {
        return SecretKeySpec("${consumerSecret.encodeOAuth()}&${accessTokenSecret?.encodeOAuth().orEmpty()}".toByteArray(), macAlgorithm)
    }

    /**
     * Creates signature.
     */
    fun signature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(macAlgorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).encodeBase64().encodeOAuth()
    }
}

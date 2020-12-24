/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

import blue.starry.penicillin.core.request.copy
import com.benasher44.uuid.uuid4
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.date.*

/**
 * OAuth cryptography utilities.
 */
public object OAuthUtil {
    /**
     * Generates random uuid string with upper case.
     */
    public val randomUUID: String
        get() = uuid4().toString().toUpperCase()

    /**
     * Current epoch time string in seconds.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public val currentEpochTime: String
        get() = "${GMTDate().timestamp / 1000}"

    /**
     * Creates initial authorization header components.
     */
    public fun initialAuthorizationHeaderComponents(consumerKey: String, accessToken: String? = null, callback: String? = null, nonce: String = randomUUID, timestamp: String = currentEpochTime): MutableMap<String, String?> {
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
    public fun signatureParam(authorizationHeaderComponent: Map<String, String?>, body: Any, parameters: ParametersBuilder, forms: ParametersBuilder): Map<String, String> {
        val result = authorizationHeaderComponent.filterValues { it != null }.map {
            it.key.encodeURLParameter() to it.value!!.encodeURLParameter()
        }.toMutableList()

        if (body !is MultiPartFormDataContent) {
            val params = parameters.copy().build() + forms.copy().build()

            params.flattenForEach { key, value ->
                result += key.encodeURLParameter() to value.encodeURLParameter()
            }
        }

        return linkedMapOf(*result.sortedBy { it.first }.toTypedArray())
    }

    /**
     * Creates signature param string.
     */
    public fun signatureParamString(param: Map<String, String>): String {
        return param.toList().joinToString("&") { "${it.first}=${it.second}" }.encodeOAuth()
    }

    /**
     * Creates signing base string.
     */
    public fun signingBaseString(httpMethod: HttpMethod, url: Url, signatureParamString: String): String {
        return "${httpMethod.value.toUpperCase()}&${url.toString().split("?").first().encodeOAuth()}&$signatureParamString"
    }

    /**
     * Creates signing key.
     */
    public fun signingKey(consumerSecret: String, accessTokenSecret: String? = null): String {
        return "${consumerSecret.encodeOAuth()}&${accessTokenSecret?.encodeOAuth().orEmpty()}"
    }

    /**
     * Creates signature.
     */
    public fun signature(signingKey: String, signatureBaseString: String): String {
        return createHmacSha1Signature(signingKey, signatureBaseString).encodeOAuth()
    }
}

internal expect fun createHmacSha1Signature(signingKey: String, signatureBaseString: String): String

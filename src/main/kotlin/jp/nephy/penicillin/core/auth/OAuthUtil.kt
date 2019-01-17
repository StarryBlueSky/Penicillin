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

package jp.nephy.penicillin.core.auth

import io.ktor.http.HttpMethod
import io.ktor.http.encodeOAuth
import io.ktor.util.InternalAPI
import io.ktor.util.date.GMTDate
import io.ktor.util.encodeBase64
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object OAuthUtil {
    fun randomUUID(): String {
        return UUID.randomUUID().toString().toUpperCase()
    }

    fun currentEpochTime(): String {
        return "${GMTDate().timestamp / 1000}"
    }

    fun signatureParamString(param: Map<String, String>): String {
        return param.toList().joinToString("&") { "${it.first}=${it.second}" }.encodeOAuth()
    }

    fun signingBaseString(httpMethod: HttpMethod, url: String, signatureParamString: String): String {
        return "${httpMethod.value}&${url.split("?").first().encodeOAuth()}&$signatureParamString"
    }

    fun signingKey(consumerSecret: String, accessTokenSecret: String?): SecretKeySpec {
        return SecretKeySpec("${consumerSecret.encodeOAuth()}&${accessTokenSecret.orEmpty().encodeOAuth()}".toByteArray(), "HmacSHA1")
    }
    
    @UseExperimental(InternalAPI::class)
    fun signature(signingKey: SecretKeySpec, signatureBaseString: String): String {
        return Mac.getInstance(signingKey.algorithm).apply {
            init(signingKey)
        }.doFinal(signatureBaseString.toByteArray()).let {
            encodeBase64(it)
        }.encodeOAuth()
    }
}

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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.session.config

import jp.nephy.penicillin.core.emulation.OfficialClient
import jp.nephy.penicillin.core.session.ApiClientDsl
import jp.nephy.penicillin.core.session.SessionBuilder

@ApiClientDsl
fun SessionBuilder.account(block: Credentials.Builder.() -> Unit) {
    getOrPutBuilder {
        Credentials.Builder()
    }.apply(block)
}

data class Credentials(
    val consumerKey: String?, val consumerSecret: String?, val accessToken: String?, val accessTokenSecret: String?, val bearerToken: String?, val knownDeviceToken: String?
): SessionConfig {
    class Builder: SessionConfigBuilder<Credentials> {
        internal var ck: String? = null
        internal var cs: String? = null
        internal var kdt: String? = null
        internal var at: String? = null
        internal var ats: String? = null
        internal var bt: String? = null

        override fun build(): Credentials {
            return Credentials(ck, cs, at, ats, bt, kdt)
        }
    }
}

fun Credentials.Builder.application(consumerKey: String, consumerSecret: String, knownDeviceToken: String? = null) {
    ck = consumerKey
    cs = consumerSecret
    kdt = knownDeviceToken
}

fun Credentials.Builder.application(client: OfficialClient.OAuth1a, knownDeviceToken: String? = null) {
    ck = client.consumerKey
    cs = client.consumerSecret
    kdt = knownDeviceToken
}

fun Credentials.Builder.token(accessToken: String, accessTokenSecret: String) {
    at = accessToken
    ats = accessTokenSecret
}

fun Credentials.Builder.token(bearerToken: String) {
    bt = bearerToken
}

fun Credentials.Builder.token(client: OfficialClient.OAuth2) {
    bt = client.bearerToken
}

internal fun SessionBuilder.createCredentials(): Credentials {
    return getOrPutBuilder {
        Credentials.Builder()
    }.build()
}

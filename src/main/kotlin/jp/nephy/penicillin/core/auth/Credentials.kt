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

package jp.nephy.penicillin.core.auth

import jp.nephy.penicillin.core.emulation.OfficialClient
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.session.config.SessionConfig
import jp.nephy.penicillin.core.session.config.SessionConfigBuilder

data class Credentials(val consumerKey: String?, val consumerSecret: String?, val accessToken: String?, val accessTokenSecret: String?, val bearerToken: String?, val knownDeviceToken: String?): SessionConfig {
    class Builder: SessionConfigBuilder<Credentials> {
        private var ck: String? = null
        private var cs: String? = null
        private var kdt: String? = null
        fun application(client: OfficialClient.OAuth1a, knownDeviceToken: String? = null) {
            ck = client.consumerKey
            cs = client.consumerSecret
            kdt = knownDeviceToken
        }

        fun application(consumerKey: String, consumerSecret: String, knownDeviceToken: String? = null) {
            ck = consumerKey
            cs = consumerSecret
            kdt = knownDeviceToken
        }

        private var at: String? = null
        private var ats: String? = null
        fun token(accessToken: String, accessTokenSecret: String) {
            at = accessToken
            ats = accessTokenSecret
        }

        private var bt: String? = null
        fun token(bearerToken: String) {
            bt = bearerToken
        }

        fun token(client: OfficialClient.OAuth2) {
            bt = client.bearerToken
        }

        override fun build(): Credentials {
            if (ck == null && cs == null && at == null && ats == null && bt == null) {
                throw PenicillinLocalizedException(LocalizedString.CredentialsAreAllNull)
            }

            return Credentials(ck, cs, at, ats, bt, kdt)
        }
    }
}

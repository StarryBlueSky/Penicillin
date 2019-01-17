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

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.models.OAuth2Token

class OAuth2(override val client: PenicillinClient): Endpoint {
    fun bearerToken(grantType: String = "client_credentials", vararg options: Pair<String, Any?>) = client.session.post("/oauth2/token") {
        authType(AuthorizationType.OAuth2RequestToken)
        body {
            form {
                add("grant_type" to grantType, *options)
            }
        }
    }.jsonObject<OAuth2Token>()

    fun invalidateToken(bearerToken: String, vararg options: Pair<String, Any?>) = client.session.post("/oauth2/invalidate_token") {
        authType(AuthorizationType.OAuth2RequestToken)
        body {
            form {
                add("access_token" to bearerToken, *options)
            }
        }
    }.jsonObject<OAuth2Token>()
}

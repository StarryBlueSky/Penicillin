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

import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.config.account
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.models.AccessTokenResponse
import jp.nephy.penicillin.models.RequestTokenResponse

val ApiClient.oauth: OAuth
    get() = OAuth(this)

class OAuth(override val client: ApiClient): Endpoint {
    suspend fun requestToken(callbackUrl: String = "oob", vararg options: Option): RequestTokenResponse {
        val result = client.session.post("/oauth/request_token") {
            authType(AuthorizationType.OAuth1a, callbackUrl)
            body {
                form {
                    add(*options)
                }
            }
        }.text().await()
        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&oauth_callback_confirmed=(.+)$".toRegex()
        val (requestToken, requestTokenSecret, callbackConfirmed) = pattern.matchEntire(result.content)!!.destructured

        return RequestTokenResponse(requestToken, requestTokenSecret, callbackConfirmed.toBoolean())
    }

    fun authorizeUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        val parameters = ParametersBuilder()
        parameters["oauth_token"] = accessToken
        if (forceLogin != null) {
            parameters["force_login"] = forceLogin.toString()
        }
        if (screenName != null) {
            parameters["screen_name"] = screenName
        }

        return URLBuilder(protocol = URLProtocol.HTTPS, host = "api.twitter.com", encodedPath = "/oauth/authorize", parameters = parameters).buildString()
    }

    fun authenticateUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        val parameters = ParametersBuilder()
        parameters["oauth_token"] = accessToken
        if (forceLogin != null) {
            parameters["force_login"] = forceLogin.toString()
        }
        if (screenName != null) {
            parameters["screen_name"] = screenName
        }

        return URLBuilder(protocol = URLProtocol.HTTPS, host = "api.twitter.com", encodedPath = "/oauth/authenticate", parameters = parameters).buildString()
    }

    suspend fun accessToken(requestToken: String, requestTokenSecret: String, verifier: String, vararg options: Option): AccessTokenResponse {
        val result = PenicillinClient {
            account {
                application(client.session.credentials.consumerKey!!, client.session.credentials.consumerSecret!!)
                token(requestToken, requestTokenSecret)
            }
        }.session.post("/oauth/access_token") {
            body {
                form {
                    add("oauth_verifier" to verifier, *options)
                }
            }
        }.text().await()
        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&user_id=(\\d+)&screen_name=(.+)$".toRegex()
        val (accessToken, accessTokenSecret, userId, screenName) = pattern.matchEntire(result.content)!!.destructured

        return AccessTokenResponse(accessToken, accessTokenSecret, userId.toLong(), screenName)
    }
}

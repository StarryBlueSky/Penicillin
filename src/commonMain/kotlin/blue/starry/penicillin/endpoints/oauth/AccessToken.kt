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

@file:Suppress("UNUSED")

package blue.starry.penicillin.endpoints.oauth

import blue.starry.penicillin.PenicillinClient
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.config.account
import blue.starry.penicillin.core.session.config.application
import blue.starry.penicillin.core.session.config.token
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.OAuth
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.oauth
import blue.starry.penicillin.extensions.use

/**
 * Represents "/oauth/access_token" response.
 */
public data class AccessTokenResponse(
    /**
     * Access token for this application.
     */
    public val accessToken: String,

    /**
     * Access token secret for this application.
     */
    public val accessTokenSecret: String,

    /**
     * User id of authenticated user.
     */
    public val userId: Long,

    /**
     * Screen name of authenticated user.
     */
    public val screenName: String
)

/**
 * Allows a Consumer application to exchange the OAuth Request Token for an OAuth Access Token. This method fulfills [Section 6.3](http://oauth.net/core/1.0/#auth_step3) of the [OAuth 1.0 authentication](http://oauth.net/core/1.0/#anchor9) flow.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/basics/authentication/api-reference/access_token)
 *
 * @param verifier If using the OAuth web-flow, set this parameter to the value of the oauth_verifier returned in the callback URL. If you are using out-of-band OAuth, set this value to the pin-code. For OAuth 1.0a compliance this parameter is required. OAuth 1.0a is strictly enforced and applications not using the oauth_verifier will fail to complete the OAuth flow.
 * @param options Optional. Custom parameters of this request.
 * @receiver [OAuth] endpoint instance.
 * @return [AccessTokenResponse].
 */
public suspend fun OAuth.accessToken(
    consumerKey: String,
    consumerSecret: String,
    requestToken: String,
    requestTokenSecret: String,
    verifier: String,
    vararg options: Option
): AccessTokenResponse {
    val response = PenicillinClient {
        account {
            application(consumerKey, consumerSecret)
            token(requestToken, requestTokenSecret)
        }
    }.use { 
        it.oauth.accessTokenInternal(verifier, *options).execute()
    }
    
    val result = response.content.split("&").map { parameter ->
        parameter.split("=", limit = 2).let { it.first() to it.last() }
    }.toMap()
    
    val accessToken = result["oauth_token"] ?: throw IllegalStateException()
    val accessTokenSecret = result["oauth_token_secret"] ?: throw IllegalStateException()
    val userId = result["user_id"]?.toLongOrNull() ?: throw IllegalStateException()
    val screenName = result["screen_name"] ?: throw IllegalStateException()

    return AccessTokenResponse(accessToken, accessTokenSecret, userId, screenName)
}

/**
 * Allows a Consumer application to exchange the OAuth Request Token for an OAuth Access Token. This method fulfills [Section 6.3](http://oauth.net/core/1.0/#auth_step3) of the [OAuth 1.0 authentication](http://oauth.net/core/1.0/#anchor9) flow.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/basics/authentication/api-reference/access_token)
 *
 * @param verifier If using the OAuth web-flow, set this parameter to the value of the oauth_verifier returned in the callback URL. If you are using out-of-band OAuth, set this value to the pin-code. For OAuth 1.0a compliance this parameter is required. OAuth 1.0a is strictly enforced and applications not using the oauth_verifier will fail to complete the OAuth flow.
 * @param options Optional. Custom parameters of this request.
 * @receiver [OAuth] endpoint instance.
 * @return [AccessTokenResponse].
 */
public suspend fun OAuth.accessToken(
    requestToken: String,
    requestTokenSecret: String,
    verifier: String,
    vararg options: Option
): AccessTokenResponse = accessToken(client.session.credentials.consumerKey!!, client.session.credentials.consumerSecret!!, requestToken, requestTokenSecret, verifier, *options)

private fun OAuth.accessTokenInternal(
    verifier: String,
    vararg options: Option
) = client.session.post("/oauth/access_token") {
    formBody(
        "oauth_verifier" to verifier,
        *options
    )
}.text()

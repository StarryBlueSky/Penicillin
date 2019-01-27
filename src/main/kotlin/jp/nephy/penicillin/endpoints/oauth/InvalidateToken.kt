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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.oauth

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.OAuth
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.OAuthToken

/**
 * Allows a registered application to revoke an issued OAuth access_token by presenting its client credentials. Once an access_token has been invalidated, new creation attempts will yield a different Access Token and usage of the invalidated token will no longer be allowed.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/basics/authentication/api-reference/invalidate_access_token)
 * 
 * @param accessToken The access_token of user to be invalidated.
 * @param accessTokenSecret The access_token_secret of user to be invalidated.
 * @param options Optional. Custom parameters of this request.
 * @receiver [OAuth] endpoint instance.
 * @return [JsonObjectApiAction] for [OAuthToken] model.
 */
fun OAuth.invalidateToken(
    accessToken: String,
    accessTokenSecret: String,
    vararg options: Option
) = invalidateTokenInternal(accessToken, accessTokenSecret, *options)

/**
 * Allows a registered application to revoke an issued OAuth access_token by presenting its client credentials. Once an access_token has been invalidated, new creation attempts will yield a different Access Token and usage of the invalidated token will no longer be allowed.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/basics/authentication/api-reference/invalidate_access_token)
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [OAuth] endpoint instance.
 * @return [JsonObjectApiAction] for [OAuthToken] model.
 */
fun OAuth.invalidateToken(
    vararg options: Option
) = invalidateTokenInternal(client.session.credentials.accessToken!!, client.session.credentials.accessTokenSecret!!, *options)

/**
 * Shorthand property to [OAuth.invalidateToken].
 * @see OAuth.invalidateToken
 */
val OAuth.invalidateToken
    get() = invalidateToken()

private fun OAuth.invalidateTokenInternal(
    accessToken: String? = null,
    accessTokenSecret: String? = null,
    vararg options: Option
) = client.session.get("/oauth/invalidate_token") {
    parameter(
        "access_token" to accessToken,
        "access_token_secret" to accessTokenSecret,
        *options
    )
}.jsonObject<OAuthToken>()

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

package blue.starry.penicillin.endpoints.oauth


import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.endpoints.OAuth
import blue.starry.penicillin.endpoints.Option

/**
 * Allows a Consumer application to use an OAuth Request Token to request user authorization. This method fulfills [Section 6.2](http://oauth.net/core/1.0/#auth_step2) of the [OAuth 1.0 authentication flow](http://oauth.net/core/1.0/#anchor9). Desktop applications must use this method (and cannot use [GET oauth/authenticate](https://developer.twitter.com/en/docs/basics/authentication/api-reference/authenticate).
 * Usage Note: An oauth_callback is never sent to this method, provide it to [POST oauth/request_token](https://developer.twitter.com/en/docs/basics/authentication/api-reference/request_token) instead.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/basics/authentication/api-reference/authorize)
 * 
 * @param forceLogin Forces the user to enter their credentials to ensure the correct users account is authorized.
 * @param screenName Prefills the username input box of the OAuth login screen with the given value.
 * @param options Optional. Custom parameters of this request.
 * @receiver [OAuth] endpoint instance.
 * @return [Url].
 */
fun OAuth.authorizeUrl(
    requestToken: String,
    forceLogin: Boolean? = null,
    screenName: String? = null,
    vararg options: Option
): Url {
    val parameters = ParametersBuilder()
    for (option in options) {
        parameters[option.first] = option.second?.toString() ?: continue
    }
    
    parameters["oauth_token"] = requestToken
    if (forceLogin != null) {
        parameters["force_login"] = forceLogin.toString()
    }
    if (screenName != null) {
        parameters["screen_name"] = screenName
    }

    return URLBuilder(
        protocol = EndpointHost.Default.protocol,
        host = EndpointHost.Default.domain,
        encodedPath = "/oauth/authorize",
        parameters = parameters
    ).build()
}

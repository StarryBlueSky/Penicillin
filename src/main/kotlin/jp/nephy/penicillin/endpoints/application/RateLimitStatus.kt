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

package jp.nephy.penicillin.endpoints.application

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Application
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.ApplicationRateLimitStatus

/**
 * Returns the current rate limits for methods belonging to the specified resource families.
 * 
 * Each API resource belongs to a "resource family" which is indicated in its method documentation. The method's resource family can be determined from the first component of the path after the resource version.
 * 
 * This method responds with a map of methods belonging to the families specified by the resources parameter, the current remaining uses for each of those resources within the current rate limiting window, and their expiration time in [epoch time](http://en.wikipedia.org/wiki/Unix_time). It also includes a rate_limit_context field that indicates the current access token or application-only authentication context.
 * 
 * You may also issue requests to this method without any parameters to receive a map of all rate limited GET methods. If your application only uses a few of methods, you should explicitly provide a resources parameter with the specified resource families you work with.
 * 
 * When using application-only auth, this method's response indicates the application-only auth rate limiting context.
 * 
 * Read more about [API Rate Limiting](https://developer.twitter.com/en/docs/basics/rate-limiting) and [review the limits](https://developer.twitter.com/en/docs/basics/rate-limits).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/get-account-settings)
 *
 * @param resources A comma-separated list of resource families you want to know the current rate limit disposition for. For best performance, only specify the resource families pertinent to your application. See [API Rate Limiting](https://developer.twitter.com/en/docs/basics/rate-limiting) for more information.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Application] endpoint instance.
 * @return [JsonObjectApiAction] for [ApplicationRateLimitStatus] model.
 */
fun Application.rateLimitStatus(
    resources: List<String>? = null,
    vararg options: Option
) = client.session.get("/1.1/application/rate_limit_status.json") {
    parameters(
        "resources" to resources?.joinToString(","),
        *options
    )
}.jsonObject { ApplicationRateLimitStatus(it, client) }

/**
 * Shorthand extension property to [Application.rateLimitStatus].
 * @see Application.rateLimitStatus
 */
val Application.rateLimitStatus
    get() = rateLimitStatus()

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

package blue.starry.penicillin.core.session

import io.ktor.http.HttpMethod
import blue.starry.penicillin.core.request.ApiRequest
import blue.starry.penicillin.core.request.ApiRequestBuilder
import blue.starry.penicillin.core.request.EndpointHost

private fun Session.call(
    method: HttpMethod, path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}
): ApiRequest {
    return ApiRequestBuilder(client, method, host, path).apply(builder).build()
}

/**
 * Creates GET api request.
 */
fun Session.get(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Get, path, host, builder)
}

/**
 * Creates POST api request.
 */
fun Session.post(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Post, path, host, builder)
}

/**
 * Creates PUT api request.
 */
fun Session.put(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Put, path, host, builder)
}

/**
 * Creates PATCH api request.
 */
fun Session.patch(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Patch, path, host, builder)
}

/**
 * Creates DELETE api request.
 */
fun Session.delete(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Delete, path, host, builder)
}

/**
 * Creates HEAD api request.
 */
fun Session.head(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Head, path, host, builder)
}

/**
 * Creates OPTIONS api request.
 */
fun Session.options(path: String, host: EndpointHost = EndpointHost.Default, builder: ApiRequestBuilder.() -> Unit = {}): ApiRequest {
    return call(HttpMethod.Options, path, host, builder)
}

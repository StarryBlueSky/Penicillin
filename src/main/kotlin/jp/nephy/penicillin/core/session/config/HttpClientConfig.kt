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

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import jp.nephy.penicillin.core.session.SessionBuilder

private var httpClientEngineFactory: HttpClientEngineFactory<*>? = null
private var httpClientConfig: HttpClientConfig<*>.() -> Unit = {}
private var httpClient: HttpClient? = null

@Suppress("UNCHECKED_CAST")
fun <T: HttpClientEngineConfig> SessionBuilder.httpClient(engineFactory: HttpClientEngineFactory<T>, block: HttpClientConfig<T>.() -> Unit = {}) {
    httpClientEngineFactory = engineFactory
    httpClientConfig = block as HttpClientConfig<*>.() -> Unit
}

fun SessionBuilder.httpClient(block: HttpClientConfig<*>.() -> Unit = {}) {
    httpClientConfig = block
}

fun SessionBuilder.httpClient(client: HttpClient) {
    httpClient = client
}

internal fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    return (httpClient ?: httpClientEngineFactory?.let { HttpClient(it) } ?: HttpClient()).config {
        block()
        httpClientConfig()
    }
}

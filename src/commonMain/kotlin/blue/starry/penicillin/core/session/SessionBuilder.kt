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

import blue.starry.penicillin.core.session.config.SessionConfigBuilder
import blue.starry.penicillin.core.session.config.createApiConfig
import blue.starry.penicillin.core.session.config.createCredentials
import blue.starry.penicillin.core.session.config.createHttpClientConfig

/**
 * The builder class that corresponds with [Session].
 */
public class SessionBuilder(private val client: ApiClient) {
    internal val configBuilders = mutableSetOf<SessionConfigBuilder<*>>()

    @PublishedApi
    internal fun build(): Session {
        val httpClientConfig = createHttpClientConfig()
        val httpClient = httpClientConfig.httpClient {
            // To handle API errors
            expectSuccess = false
        }

        return Session(client, httpClient, createCredentials(), createApiConfig(), httpClientConfig.shouldClose)
    }
}

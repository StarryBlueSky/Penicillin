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

package jp.nephy.penicillin.core.session

import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.cookies.addCookie
import io.ktor.util.KtorExperimentalAPI
import jp.nephy.penicillin.core.session.config.*
import kotlinx.coroutines.runBlocking

/**
 * The builder class that corresponds with [Session].
 */
class SessionBuilder(private val client: ApiClient) {
    internal val configBuilders = mutableSetOf<SessionConfigBuilder<*>>()
    
    internal fun build(): Session {
        val cookieConfig = createCookieConfig()
        val dispatcherConfig = createDispatcherConfig()
        val httpClientConfig = createHttpClientConfig()
        val httpClient = httpClientConfig.httpClient {
            if (cookieConfig.acceptCookie) {
                install(HttpCookies) {
                    storage = AcceptAllCookiesStorage()

                    if (cookieConfig.cookies.isNotEmpty()) {
                        for ((key, cookies) in cookieConfig.cookies) {
                            for (it in cookies) {
                                runBlocking {
                                    storage.addCookie(key, it)
                                }
                            }
                        }
                    }
                }
            }
            
            // To handle API errors
            expectSuccess = false
        }
        
        if (dispatcherConfig.connectionThreadsCount != null) {
            @UseExperimental(KtorExperimentalAPI::class)
            httpClient.engineConfig.threadsCount = dispatcherConfig.connectionThreadsCount
        }

        return Session(client, httpClient, dispatcherConfig.coroutineContext, dispatcherConfig.shouldClose, createCredentials(), createApiConfig(), httpClientConfig.shouldClose)
    }
}

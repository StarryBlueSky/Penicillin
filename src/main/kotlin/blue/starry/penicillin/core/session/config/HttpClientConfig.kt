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

package blue.starry.penicillin.core.session.config

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import blue.starry.penicillin.core.session.ApiClientDsl
import blue.starry.penicillin.core.session.SessionBuilder

/**
 * Creates [HttpClient] with engine factory and config block.
 */
@ApiClientDsl
@Suppress("UNCHECKED_CAST")
fun <T: HttpClientEngineConfig> SessionBuilder.httpClient(engineFactory: HttpClientEngineFactory<T>, block: HttpClientConfig<T>.() -> Unit = {}) {
    getOrPutBuilder { 
        KtorHttpClientConfig.Builder()
    }.apply {
        this.engineFactory = engineFactory
        this.clientConfigs.add(block as HttpClientConfig<*>.() -> Unit)
    }
}

/**
 * Creates [HttpClient] with default engine factory and engine config block
 */
@ApiClientDsl
fun SessionBuilder.httpClient(block: HttpClientConfig<*>.() -> Unit = {}) {
    getOrPutBuilder {
        KtorHttpClientConfig.Builder()
    }.apply {
        this.clientConfigs.add(block)
    }
}

/**
 * Creates [HttpClient] with existing client.
 */
@ApiClientDsl
fun SessionBuilder.httpClient(client: HttpClient) {
    getOrPutBuilder {
        KtorHttpClientConfig.Builder()
    }.apply {
        this.client = client
    }
}

internal data class KtorHttpClientConfig(
    val engineFactory: HttpClientEngineFactory<*>?,
    val clientConfigs: List<HttpClientConfig<*>.() -> Unit>,
    val client: HttpClient?,
    val shouldClose: Boolean
): SessionConfig {
    fun httpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return (client ?: engineFactory?.let { HttpClient(it) } ?: HttpClient()).config { 
            block()
            
            for (config in clientConfigs) {
                config()
            }
        }
    }
    
    internal class Builder: SessionConfigBuilder<KtorHttpClientConfig> {
        var engineFactory: HttpClientEngineFactory<*>? = null
        var clientConfigs = mutableListOf<HttpClientConfig<*>.() -> Unit>()
        var client: HttpClient? = null
        
        override fun build(): KtorHttpClientConfig {
            return KtorHttpClientConfig(engineFactory, clientConfigs, client, client == null)
        }
    }
}

internal fun SessionBuilder.createHttpClientConfig(): KtorHttpClientConfig {
    return getOrPutBuilder {
        KtorHttpClientConfig.Builder()
    }.build()
}

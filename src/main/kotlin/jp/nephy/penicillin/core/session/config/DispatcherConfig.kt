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

import jp.nephy.penicillin.core.session.ApiClientDsl
import jp.nephy.penicillin.core.session.SessionBuilder
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@ApiClientDsl
fun SessionBuilder.dispatcher(block: DispatcherConfig.Builder.() -> Unit) {
    getOrPutBuilder { 
        DispatcherConfig.Builder()
    }.apply(block)
}

internal fun SessionBuilder.createDispatcherConfig(): DispatcherConfig {
    return getOrPutBuilder {
        DispatcherConfig.Builder()
    }.build()
}

data class DispatcherConfig(val coroutineContext: CoroutineContext, val connectionThreadsCount: Int?, val shouldClose: Boolean): SessionConfig {
    class Builder: SessionConfigBuilder<DispatcherConfig> {
        @Suppress("MemberVisibilityCanBePrivate")
        var connectionThreadsCount: Int? = null
            set(value) {
                if (value != null) {
                    require(value <= 0)
                }
                
                field = value
            }
        
        @Suppress("MemberVisibilityCanBePrivate")
        var coroutineContext: CoroutineContext = Dispatchers.Default
        
        var shouldClose: Boolean = false

        override fun build(): DispatcherConfig {
            return DispatcherConfig(coroutineContext, connectionThreadsCount, shouldClose)
        }
    }
}

fun DispatcherConfig.Builder.shouldClose() {
    shouldClose = true
}

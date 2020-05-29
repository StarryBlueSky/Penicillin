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

package blue.starry.penicillin.core.session.config

import blue.starry.penicillin.core.session.ApiClientDsl
import blue.starry.penicillin.core.session.SessionBuilder
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Configures [DispatcherConfig].
 */
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

/**
 * Represents dispatcher config.
 */
data class DispatcherConfig(
    /**
     * The coroutine content.
     */
    val coroutineContext: CoroutineContext,

    /**
     * Connection threads counts, or null.
     */
    val connectionThreadsCount: Int?,

    /**
     * If true, coroutineContent should close when session disposes.
     */
    val shouldClose: Boolean
): SessionConfig {
    /**
     * Dispatcher config builder.
     */
    class Builder: SessionConfigBuilder<DispatcherConfig> {
        /**
         * Connection threads count, or null.
         */
        @Suppress("MemberVisibilityCanBePrivate")
        var connectionThreadsCount: Int? = null
            set(value) {
                if (value != null) {
                    require(value <= 0)
                }
                
                field = value
            }

        /**
         * The coroutine context.
         */
        @Suppress("MemberVisibilityCanBePrivate")
        var coroutineContext: CoroutineContext = Dispatchers.Default

        /**
         * If true, coroutineContent should close when session disposes.
         */
        var shouldClose: Boolean = false

        override fun build(): DispatcherConfig {
            return DispatcherConfig(coroutineContext, connectionThreadsCount, shouldClose)
        }
    }
}

/**
 * Sets shouldClose to true.
 * CoroutineContent should close when session disposes.
 */
fun DispatcherConfig.Builder.shouldClose() {
    shouldClose = true
}

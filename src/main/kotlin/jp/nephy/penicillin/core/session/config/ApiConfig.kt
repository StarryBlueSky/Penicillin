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

import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.core.session.SessionBuilder
import java.util.concurrent.TimeUnit

fun SessionBuilder.api(block: ApiConfig.Builder.() -> Unit) {
    getOrPutBuilder { 
        ApiConfig.Builder()
    }.apply(block)
}

internal fun SessionBuilder.createApiConfig(): ApiConfig {
    return getOrPutBuilder {
        ApiConfig.Builder()
    }.build()
}

data class ApiConfig(val maxRetries: Int, val retryInMillis: Long, val defaultTimeoutInMillis: Long, val emulationMode: EmulationMode, val skipEmulationChecking: Boolean): SessionConfig {
    companion object {
        private const val defaultMaxRetries = 3
        private const val defaultRetryIntervalInMillis = 3000L
        private const val defaultTimeoutInMillis = 5000L
    }
    
    class Builder: SessionConfigBuilder<ApiConfig> {
        @Suppress("MemberVisibilityCanBePrivate")
        var maxRetries: Int = defaultMaxRetries
            set(value) {
                require(value >= 0)
                
                field = value
            }
        
        private var retryIntervalInMillis = defaultRetryIntervalInMillis
        fun retryInterval(interval: Long, unit: TimeUnit) {
            require(interval >= 0)
            
            retryIntervalInMillis = unit.toMillis(interval)
        }

        private var timeoutInMillis = defaultTimeoutInMillis
        fun defaultTimeout(timeout: Long, unit: TimeUnit) {
            require(timeout > 0)
            
            timeoutInMillis = unit.toMillis(timeout)
        }

        @PenicillinExperimentalApi
        var emulationMode: EmulationMode = EmulationMode.None
        
        private var skipEmulationChecking = false
        fun skipEmulationChecking() {
            skipEmulationChecking = true
        }
        
        @UseExperimental(PenicillinExperimentalApi::class)
        override fun build(): ApiConfig {
            return ApiConfig(maxRetries, retryIntervalInMillis, timeoutInMillis, emulationMode, skipEmulationChecking)
        }
    }
}

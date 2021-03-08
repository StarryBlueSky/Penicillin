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

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.session.ApiClientDsl
import blue.starry.penicillin.core.session.SessionBuilder
import blue.starry.penicillin.endpoints.common.TweetMode

/**
 * Creates [ApiConfig] configurations.
 * Provides timeout, retries, emulations and so on.
 * 
 * @see ApiConfig
 */
@ApiClientDsl
public fun SessionBuilder.api(block: ApiConfig.Builder.() -> Unit) {
    getOrPutBuilder { 
        ApiConfig.Builder()
    }.apply(block)
}

internal fun SessionBuilder.createApiConfig(): ApiConfig {
    return getOrPutBuilder {
        ApiConfig.Builder()
    }.build()
}

/**
 * Configurations related to api execution.
 * @see SessionBuilder.api
 */
public data class ApiConfig(
    /**
     * Max retry count of single request.
     */
    public val maxRetries: Int,

    /**
     * Retry interval in milli seconds
     */
    public val retryInMillis: Long,

    /**
     * EmulationMode which is used to access Twitter Private endpoints.
     */
    public val emulationMode: EmulationMode,

    /**
     * Skips emulationMode checking if true
     */
    public val skipEmulationChecking: Boolean,

    /**
     * Default value for tweetMode parameter.
     */
    public val defaultTweetMode: TweetMode
): SessionConfig {
    
    /**
     * Provides ApiConfig builder.
     */
    public class Builder: SessionConfigBuilder<ApiConfig> {
        public companion object {
            private const val defaultMaxRetries = 3
            private const val defaultRetryIntervalInMillis = 3000L
            private const val defaultTimeoutInMillis = 5000L
        }

        /**
         * Sets max retry count of single request.
         */
        @Suppress("MemberVisibilityPrivate")
        public var maxRetries: Int = defaultMaxRetries
            set(value) {
                require(value >= 0)
                
                field = value
            }
        
        /**
         * Sets retry interval in milli seconds.
         * If your request failed, reattempt after this value.
         * @see ApiConfig.Builder.retryInterval
         */
        public var retryIntervalInMillis: Long = defaultRetryIntervalInMillis
            set(value) {
                require(value >= 0)

                field = value
            }
        
        /**
         * Sets emulationMode which is used to access Twitter Private endpoints.
         * For example, to access Cards API, you must set this [EmulationMode.TwitterForiPhone].
         */
        public var emulationMode: EmulationMode = EmulationMode.None
        
        /**
         * Skips emulationMode checking if true.
         * It means that you may access Twitter Private endpoints despite using non-official application.
         * @see ApiConfig.Builder.skipEmulationChecking
         */
        public var skipEmulationChecking: Boolean = false
        
        /**
         * Sets default value for tweetMode parameter.
         * Learn more at [Tweet updates](https://developer.twitter.com/en/docs/tweets/tweet-updates).
         */
        @Suppress("MemberVisibilityPrivate")
        public var defaultTweetMode: TweetMode = TweetMode.Extended

        override fun build(): ApiConfig {
            return ApiConfig(maxRetries, retryIntervalInMillis, emulationMode, skipEmulationChecking, defaultTweetMode)
        }
    }
}

/**
 * Sets retry interval in millis.
 */
public fun ApiConfig.Builder.retryInterval(intervalInMillis: Long) {
    retryIntervalInMillis = intervalInMillis
}

/**
 * Skips emulationMode checking.
 */
public fun ApiConfig.Builder.skipEmulationChecking() {
    skipEmulationChecking = true
}

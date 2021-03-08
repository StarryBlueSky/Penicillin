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

import blue.starry.penicillin.core.exceptions.PenicillinException
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.core.session.config.ApiConfig
import blue.starry.penicillin.core.session.config.Credentials
import io.ktor.client.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive

/**
 * Penicillin Session instance.
 * Provides HttpClient, credentials, options.
 */
public data class Session(
    /**
     * ApiClient instance.
     */
    public val client: ApiClient,
    
    private val underlyingHttpClient: HttpClient,

    /**
     * Account credentials.
     */
    public val credentials: Credentials,

    /**
     * Api configurations.
     */
    public val option: ApiConfig,
    
    private val shouldCloseHttpClient: Boolean
): Closeable {
    private val job = Job()
    
    /**
     * Ktor HttpClient instance.
     * Throws SessionAlreadyClosed when session is already closed.
     */
    public val httpClient: HttpClient
        get() = if (job.isActive && underlyingHttpClient.coroutineContext.isActive) {
            underlyingHttpClient
        } else {
            throw PenicillinException(LocalizedString.SessionAlreadyClosed)
        }

    /**
     * Closes HttpClient if shouldCloseHttpClient is true and coroutine dispatcher if shouldCloseCoroutineContext == true.
     */
    override fun close() {
        job.cancel()
        
        if (shouldCloseHttpClient) {
            underlyingHttpClient.close()
        }
    }
}

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

import io.ktor.client.HttpClient
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.session.config.ApiConfig
import jp.nephy.penicillin.core.session.config.Credentials
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import kotlinx.io.core.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * Penicillin Session instance.
 * Provides HttpClient, credentials, options.
 */
data class Session(
    /**
     * ApiClient instance.
     */
    val client: ApiClient,
    
    private val underlyingHttpClient: HttpClient,
    override val coroutineContext: CoroutineContext,
    private val shouldClose: Boolean,

    /**
     * Account credentials.
     */
    val credentials: Credentials,

    /**
     * Api configurations.
     */
    val option: ApiConfig
): Closeable, CoroutineScope {
    private val isActive = atomic(true)
    
    /**
     * Ktor HttpClient instance.
     * Throws SessionAlreadyClosed when session is already closed.
     */
    val httpClient: HttpClient
        get() = if (isActive.value && underlyingHttpClient.coroutineContext.isActive) {
            underlyingHttpClient
        } else {
            throw PenicillinLocalizedException(LocalizedString.SessionAlreadyClosed)
        }

    /**
     * Closes HttpClient and coroutine dispatcher if shouldClose is true.
     */
    override fun close() {
        isActive.value = false
        underlyingHttpClient.close()
        
        if (shouldClose && coroutineContext is Closeable) {
            coroutineContext.close()
        }
    }
}

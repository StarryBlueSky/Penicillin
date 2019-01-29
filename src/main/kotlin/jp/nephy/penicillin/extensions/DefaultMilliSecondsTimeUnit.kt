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

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.core.session.config.ApiConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

fun ApiConfig.Builder.retryInterval(intervalInMillis: Long) {
    retryInterval(intervalInMillis, TimeUnit.MILLISECONDS)
}

fun ApiConfig.Builder.defaultTimeout(timeoutInMillis: Long) {
    defaultTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R: Any> ApiAction<R>.awaitWithTimeout(timeoutInMillis: Long): R? {
    return awaitWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class, CancellationException::class)
fun <R: Any> ApiAction<R>.deferWithTimeout(timeoutInMillis: Long): Deferred<R?> {
    return deferWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class)
fun <R: Any> ApiAction<R>.completeWithTimeout(timeoutInMillis: Long): R? {
    return completeWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS, onFailure, onSuccess)
}

inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, onSuccess)
}

fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, {})
}

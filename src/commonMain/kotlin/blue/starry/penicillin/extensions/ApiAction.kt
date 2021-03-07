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

package blue.starry.penicillin.extensions

import blue.starry.penicillin.core.exceptions.PenicillinException
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.core.request.action.ApiAction
import kotlinx.coroutines.*
import mu.KotlinLogging

/**
 * Awaits api execution and returns its result with timeout.
 * This function is suspend-function.
 *
 * @param timeoutInMillis Timeout value in millis.
 *
 * @return Api result as [R]. If the timeout exceeds, returns null.
 *
 * @throws PenicillinException General Penicillin exceptions.
 * @throws CancellationException Thrown when coroutine scope is cancelled.
 */
public suspend fun <R: Any> ApiAction<R>.executeWithTimeout(timeoutInMillis: Long): R? {
    return withTimeoutOrNull(timeoutInMillis) {
        execute()
    }
}

/**
 * Awaits api execution and returns its result with user-defined or default timeout.
 * This function is suspend-function.
 *
 * @return Api result as [R]. If the timeout exceeds, returns null.
 * 
 * @throws PenicillinException General Penicillin exceptions.
 * @throws CancellationException Thrown when coroutine scope is cancelled.
 */
public suspend fun <R: Any> ApiAction<R>.executeWithTimeout(): R? {
    return executeWithTimeout(session.option.defaultTimeoutInMillis)
}

/**
 * Creates [Deferred] object for api execution.
 * 
 * @return [Deferred] object for api execution.
 */
public fun <R: Any> ApiAction<R>.executeAsync(): Deferred<R> {
    return session.async {
        execute()
    }
}

private val defaultLogger = KotlinLogging.logger("Penicillin.Client")

internal typealias ApiCallback<R> = suspend (response: R) -> Unit
internal typealias ApiFallback = suspend (e: Throwable) -> Unit

@PublishedApi
internal val <R: Any> ApiAction<R>.defaultApiCallback: ApiCallback<R>
    get() = {}

@PublishedApi
internal val ApiAction<*>.defaultApiFallback: ApiFallback
    get() = {
        defaultLogger.error(it) { LocalizedString.ExceptionInAsyncBlock }
    }

/**
 * Creates [Job] for api execution.
 * 
 * @param onFailure Api fallback.
 * @param onSuccess Api callback.
 * 
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queue(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return session.launch {
        runCatching {
            execute()
        }.onSuccess {
            onSuccess.invoke(it)
        }.onFailure {
            onFailure.invoke(it)
        }
    }
}

/**
 * Creates [Job] for api execution with default api fallback.
 *
 * @param onSuccess Api callback.
 *
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queue(crossinline onSuccess: ApiCallback<R>): Job {
    return queue(defaultApiFallback, onSuccess)
}

/**
 * Creates [Job] for api execution with default api fallback and default api callback.
 *
 * @return [Job] for api execution.
 */
public fun <R: Any> ApiAction<R>.queue(): Job {
    return queue(defaultApiFallback, defaultApiCallback)
}

/**
 * Creates [Job] for api execution with timeout.
 *
 * @param timeoutInMillis Timeout value in millis.
 * @param onFailure Api fallback.
 * @param onSuccess Api callback.
 *
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return session.launch {
        runCatching {
            withTimeout(timeoutInMillis) {
                execute()
            }
        }.onSuccess {
            onSuccess.invoke(it)
        }.onFailure {
            onFailure.invoke(it)
        }
    }
}

/**
 * Creates [Job] for api execution with timeout and default api fallback.
 *
 * @param timeoutInMillis Timeout value.
 * @param onSuccess Api callback.
 *
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, onSuccess)
}

/**
 * Creates [Job] for api execution with timeout and default api fallback, default api callback.
 *
 * @param timeoutInMillis Timeout value in millis.
 *
 * @return [Job] for api execution.
 */
public fun <R: Any> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, {})
}

/**
 * Creates [Job] for api execution with user-defined or default timeout.
 *
 * @param onFailure Api fallback.
 * @param onSuccess Api callback.
 *
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queueWithTimeout(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(session.option.defaultTimeoutInMillis, onFailure, onSuccess)
}

/**
 * Creates [Job] for api execution with user-defined or default timeout and default api fallback.
 *
 * @param onSuccess Api callback.
 *
 * @return [Job] for api execution.
 */
public inline fun <R: Any> ApiAction<R>.queueWithTimeout(crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(defaultApiFallback, onSuccess)
}

/**
 * Creates [Job] for api execution with user-defined or default timeout, default api fallback and default api callback.
 *
 * @return [Job] for api execution.
 */
public fun <R: Any> ApiAction<R>.queueWithTimeout(): Job {
    return queueWithTimeout(defaultApiFallback, defaultApiCallback)
}

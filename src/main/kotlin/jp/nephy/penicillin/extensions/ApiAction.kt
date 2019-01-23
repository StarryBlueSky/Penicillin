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
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.ApiAction
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.util.concurrent.TimeUnit

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R: Any> ApiAction<R>.awaitWithTimeout(timeout: Long, unit: TimeUnit): R? {
    return withTimeoutOrNull(unit.toMillis(timeout)) {
        await()
    }
}

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R: Any> ApiAction<R>.awaitWithTimeout(): R? {
    return awaitWithTimeout(session.option.defaultTimeoutInMillis)
}

@Throws(PenicillinException::class)
fun <R: Any> ApiAction<R>.complete(): R {
    return runBlocking(session.coroutineContext) {
        await()
    }
}

@Throws(PenicillinException::class)
fun <R: Any> ApiAction<R>.completeWithTimeout(timeout: Long, unit: TimeUnit): R? {
    return runBlocking(session.coroutineContext) {
        awaitWithTimeout(timeout, unit)
    }
}

@Throws(PenicillinException::class)
fun <R: Any> ApiAction<R>.completeWithTimeout(): R? {
    return completeWithTimeout(session.option.defaultTimeoutInMillis)
}

internal typealias ApiCallback<R> = suspend (response: R) -> Unit
internal typealias ApiFallback = suspend (e: Throwable) -> Unit

private val defaultLogger = KotlinLogging.logger("Penicillin.Client")
val ApiAction<*>.defaultApiFallback: ApiFallback
    get() = {
        defaultLogger.error(it) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

/*
    queue
 */

inline fun <R: Any> ApiAction<R>.queue(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return session.launch {
        runCatching {
            await()
        }.onSuccess {
            onSuccess.invoke(it)
        }.onFailure {
            onFailure.invoke(it)
        }
    }
}

inline fun <R: Any> ApiAction<R>.queue(crossinline onSuccess: ApiCallback<R>): Job {
    return queue(defaultApiFallback, onSuccess)
}

fun <R: Any> ApiAction<R>.queue(): Job {
    return queue(defaultApiFallback, {})
}

/*
    queueWithTimeout
 */

inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit, crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return session.launch {
        runCatching {
            withTimeout(unit.toMillis(timeout)) {
                await()
            }
        }.onSuccess {
            onSuccess.invoke(it)
        }.onFailure {
            onFailure.invoke(it)
        }
    }
}

inline fun <R: Any> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeout, unit, defaultApiFallback, onSuccess)
}

fun <R: Any> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit): Job {
    return queueWithTimeout(timeout, unit, defaultApiFallback, {})
}

inline fun <R: Any> ApiAction<R>.queueWithTimeout(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(session.option.defaultTimeoutInMillis, onFailure, onSuccess)
}

inline fun <R: Any> ApiAction<R>.queueWithTimeout(crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(defaultApiFallback, onSuccess)
}

fun <R: Any> ApiAction<R>.queueWithTimeout(): Job {
    return queueWithTimeout(defaultApiFallback, {})
}

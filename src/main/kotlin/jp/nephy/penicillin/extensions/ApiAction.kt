@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.ApiAction
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R> ApiAction<R>.awaitWithTimeout(timeout: Long, unit: TimeUnit): R? {
    return withTimeoutOrNull(unit.toMillis(timeout)) {
        await()
    }
}

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R> ApiAction<R>.awaitWithTimeout(): R? {
    return awaitWithTimeout(session.option.defaultTimeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class)
fun <R> ApiAction<R>.complete(): R {
    return runBlocking(session.coroutineContext) {
        await()
    }
}

@Throws(PenicillinException::class)
fun <R> ApiAction<R>.completeWithTimeout(timeout: Long, unit: TimeUnit): R? {
    return runBlocking(session.coroutineContext) {
        awaitWithTimeout(timeout, unit)
    }
}

@Throws(PenicillinException::class)
fun <R> ApiAction<R>.completeWithTimeout(): R? {
    return completeWithTimeout(session.option.defaultTimeoutInMillis, TimeUnit.MILLISECONDS)
}

private typealias ApiCallback<R> = suspend (response: R) -> Unit
private typealias ApiFallback = suspend (e: Throwable) -> Unit

val ApiAction<*>.defaultApiFallback: ApiFallback
    get() = {
        session.logger.error(it) { LocalizedString.ExceptionInAsyncBlock.format() }
    }

/*
    queue
 */

inline fun <R> ApiAction<R>.queue(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
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

inline fun <R> ApiAction<R>.queue(crossinline onSuccess: ApiCallback<R>): Job {
    return queue(defaultApiFallback, onSuccess)
}

fun <R> ApiAction<R>.queue(): Job {
    return queue(defaultApiFallback, {})
}

/*
    queueWithTimeout
 */

inline fun <R> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit, crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
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

inline fun <R> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeout, unit, defaultApiFallback, onSuccess)
}

fun <R> ApiAction<R>.queueWithTimeout(timeout: Long, unit: TimeUnit): Job {
    return queueWithTimeout(timeout, unit, defaultApiFallback, {})
}

inline fun <R> ApiAction<R>.queueWithTimeout(crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(session.option.defaultTimeoutInMillis, TimeUnit.MILLISECONDS, onFailure, onSuccess)
}

inline fun <R> ApiAction<R>.queueWithTimeout(crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(defaultApiFallback, onSuccess)
}

fun <R> ApiAction<R>.queueWithTimeout(): Job {
    return queueWithTimeout(defaultApiFallback, {})
}

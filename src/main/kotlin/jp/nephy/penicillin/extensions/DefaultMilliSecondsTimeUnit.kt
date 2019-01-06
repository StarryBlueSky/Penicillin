@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.core.session.SessionBuilder
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import java.util.concurrent.TimeUnit

fun SessionBuilder.retry(intervalInMillis: Long) {
    retry(intervalInMillis, TimeUnit.MILLISECONDS)
}

fun SessionBuilder.defaultTimeout(timeoutInMillis: Long) {
    defaultTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class, CancellationException::class)
suspend fun <R> ApiAction<R>.awaitWithTimeout(timeoutInMillis: Long): R? {
    return awaitWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

@Throws(PenicillinException::class)
fun <R> ApiAction<R>.completeWithTimeout(timeoutInMillis: Long): R? {
    return completeWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS)
}

inline fun <R> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onFailure: ApiFallback, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeoutInMillis, TimeUnit.MILLISECONDS, onFailure, onSuccess)
}

inline fun <R> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long, crossinline onSuccess: ApiCallback<R>): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, onSuccess)
}

fun <R> ApiAction<R>.queueWithTimeout(timeoutInMillis: Long): Job {
    return queueWithTimeout(timeoutInMillis, defaultApiFallback, {})
}

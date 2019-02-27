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

import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.response.ApiResponse
import kotlinx.coroutines.delay
import java.util.*

val ApiResponse<*>.rateLimit: RateLimit?
    get() = response.rateLimit

val PenicillinException.rateLimit: RateLimit?
    get() = response?.rateLimit

private val HttpResponse.rateLimit: RateLimit?
    get() {
        val limit = headers["x-rate-limit-limit"]?.toIntOrNull() ?: return null
        val remaining = headers["x-rate-limit-remaining"]?.toIntOrNull() ?: return null
        val reset = headers["x-rate-limit-reset"]?.toLongOrNull() ?: return null
        val resetAt = Calendar.getInstance().also {
            it.timeInMillis = reset * 1000
        }

        return RateLimit(limit, remaining, resetAt)
    }

/**
 * Twitter API rate limit.
 */
data class RateLimit(
    /**
     * Endpoint max limit in 15 minutes.
     */
    val limit: Int,

    /**
     * Current remaining for endpoint.
     */
    val remaining: Int,

    /**
     * Rate limit reset at.
     */
    val resetAt: Calendar
)

/**
 * The flag indicates you may access the same endpoint more at this time. If true, rate limit exceeded.
 */
val RateLimit.isExceeded: Boolean
    get() = remaining == 0
/**
 * The count you consumed in last 15 minutes.
 */
val RateLimit.consumed: Int?
    get() = limit - remaining

/**
 * Awaits until rate limit is refreshed. (Suspending function)
 */
suspend fun RateLimit.awaitRefresh() {
    val millis = resetAt.timeInMillis - Date().time
    if (millis > 0) {
        delay(millis.coerceAtLeast(500))
    }
}

/**
 * Blocks until rate limit is refreshed. (Classic blocking function)
 */
fun RateLimit.blockUntilRefresh() {
    val millis = resetAt.timeInMillis - Date().time
    if (millis > 0) {
        Thread.sleep(millis.coerceAtLeast(500))
    }
}

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

import jp.nephy.penicillin.core.response.ApiResponse
import java.util.*

val ApiResponse<*>.rateLimit: RateLimit
    get() {
        val limit = response.headers["x-rate-limit-limit"]?.toIntOrNull()
        val remaining = response.headers["x-rate-limit-remaining"]?.toIntOrNull()
        val reset = response.headers["x-rate-limit-reset"]?.toLongOrNull()

        val resetAt = reset?.let {
            Calendar.getInstance().apply {
                timeInMillis = it
            }
        }

        return RateLimit(limit, remaining, resetAt)
    }

data class RateLimit(val limit: Int?, val remaining: Int?, val resetAt: Calendar?)

val RateLimit.hasLimit: Boolean
    get() = limit != null && remaining != null

val RateLimit.isExceeded: Boolean
    get() = remaining == 0

val RateLimit.consumed: Int?
    get() = remaining?.also { limit?.minus(it) }

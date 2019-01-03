@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.response.ApiResponse
import java.util.*

val ApiResponse.rateLimit: RateLimit
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

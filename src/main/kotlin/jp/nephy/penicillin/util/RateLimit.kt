package jp.nephy.penicillin.util

import okhttp3.Headers

class RateLimit(headers: Headers) {
    val limit = headers["x-rate-limit-limit"]?.toIntOrNull()
    val remaining = headers["x-rate-limit-remaining"]?.toIntOrNull()
    private val resetAtLong = headers["x-rate-limit-reset"]?.toLongOrNull()
    val resetAt = if (resetAtLong != null) {
        EpochTime(resetAtLong)
    } else {
        null
    }

    val hasLimit: Boolean
        get() = limit != null && remaining != null
    val hasExceeded: Boolean
        get() = remaining == 0

    val currentLimit: Int
        get() = if (limit != null && remaining != null) {
            limit - remaining
        } else {
            0
        }
}

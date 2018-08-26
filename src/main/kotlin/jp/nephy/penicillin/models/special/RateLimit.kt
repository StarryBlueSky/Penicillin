package jp.nephy.penicillin.models.special

import okhttp3.Headers
import java.util.*

class RateLimit(headers: Headers) {
    val limit = headers["x-rate-limit-limit"]?.toIntOrNull()
    val remaining = headers["x-rate-limit-remaining"]?.toIntOrNull()
    private val resetAtLong = headers["x-rate-limit-reset"]?.toLongOrNull()
    val resetAt = if (resetAtLong != null) {
        Date(resetAtLong * 1000)
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

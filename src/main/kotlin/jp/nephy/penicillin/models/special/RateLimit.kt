package jp.nephy.penicillin.models.special

import java.util.*

class RateLimit(limit: String?, remaining: String?, reset: String?) {
    val limit = limit?.toIntOrNull()
    val remaining = remaining?.toIntOrNull()

    private val resetAtLong = reset?.toLongOrNull()
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

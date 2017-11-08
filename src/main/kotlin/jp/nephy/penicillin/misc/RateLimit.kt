package jp.nephy.penicillin.misc

import okhttp3.Headers
import java.util.*

@Suppress("UNUSED")
class RateLimit(headers: Headers) {
    val limit = headers.get("x-rate-limit-limit")?.toIntOrNull()
    val remaining = headers.get("x-rate-limit-remaining")?.toIntOrNull()
    val resetAt = headers["x-rate-limit-reset"]?.toLongOrNull()
    val resetAtEpoch = if (resetAt != null) EpochTime(resetAt) else (null)

    fun hasLimit(): Boolean = limit != null && remaining != null

    fun isExceeded(): Boolean = remaining == 0

    fun getCurrentLimit(): Int? = if (limit != null && remaining != null) limit - remaining else (null)

    fun getResetAt(): Date? = resetAtEpoch?.toDate()
}

package jp.nephy.penicillin.api

import java.util.*

class RateLimit(header: Map<String,List<String>>) {
    val limit = header["x-rate-limit-limit"]?.get(0)?.toIntOrNull() ?: 0
    val remaining = header["x-rate-limit-remaining"]?.get(0)?.toIntOrNull() ?: 0
    val resetAt = EpochTime(header["x-rate-limit-reset"]?.get(0)?.toLongOrNull() ?: 0)

    fun isExceeded(): Boolean = remaining == 0

    fun getCurrentLimit(): Int = limit - remaining

    fun getResetAt(): Date = resetAt.toDate()
}

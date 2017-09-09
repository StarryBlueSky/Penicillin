package jp.nephy.penicillin.api

import jp.nephy.penicillin.api.model.EpochTime
import java.util.*

class RateLimit(val limit: Int, val remaining: Int, val resetAt: EpochTime) {
    fun isExceeded(): Boolean {
        return remaining == 0
    }

    fun getCurrentLimit(): Int {
        return limit - remaining
    }

    fun getResetAt(): Date {
        return resetAt.toDate()
    }
}

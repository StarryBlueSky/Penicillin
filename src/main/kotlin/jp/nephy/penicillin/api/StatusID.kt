package jp.nephy.penicillin.api

import java.util.*

class StatusID(private val statusId: Long) {
    fun toLong(): Long {
        return statusId
    }

    override fun toString(): String {
        return statusId.toString()
    }

    fun toDate(): Date {
        return Date((statusId shr 22) + 1288834974657)
    }
}

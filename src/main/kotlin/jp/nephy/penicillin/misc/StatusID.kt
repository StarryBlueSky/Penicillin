package jp.nephy.penicillin.misc

import java.util.*

@Suppress("UNUSED")
class StatusID(private val statusId: Long) {
    fun toLong(): Long = statusId

    override fun toString(): String = statusId.toString()

    fun toDate(): Date = Date((statusId shr 22) + 1288834974657)
}

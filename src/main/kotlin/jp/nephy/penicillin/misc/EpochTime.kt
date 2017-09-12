package jp.nephy.penicillin.misc

import java.util.*

class EpochTime(private val time: Long) {
    fun toDate(): Date {
        if (time < 10000000000) {
            return Date(time * 1000)
        }
        return Date(time)
    }

    override fun toString(): String = toDate().toString()
}

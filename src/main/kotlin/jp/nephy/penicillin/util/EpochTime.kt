package jp.nephy.penicillin.util

import java.util.*

class EpochTime(val value: Long) {
    val date: Date
        get() = if (value < 1e10) {
            Date(value * 1000)
        } else {
            Date(value)
        }
}

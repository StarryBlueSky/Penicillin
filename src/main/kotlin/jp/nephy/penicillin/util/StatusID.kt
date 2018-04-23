package jp.nephy.penicillin.util

import java.util.*


class StatusID(val value: Long) {
    val date: Date
        get() = Date((value shr 22) + 1288834974657)
}

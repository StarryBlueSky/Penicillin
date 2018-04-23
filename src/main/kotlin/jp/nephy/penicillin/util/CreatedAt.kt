package jp.nephy.penicillin.util

import java.text.SimpleDateFormat
import java.util.*

private const val createdAtPattern = "EEE, dd MMM yyyy HH:mm:ss ZZZZZ"

data class CreatedAt(val value: String) {
    val date: Date
        get() = SimpleDateFormat(createdAtPattern).parse(value)
}

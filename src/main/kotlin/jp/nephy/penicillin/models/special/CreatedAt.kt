@file:Suppress("UNUSED")

package jp.nephy.penicillin.models.special

import java.text.SimpleDateFormat
import java.util.*

private const val createdAtPattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"

data class CreatedAt(val value: String) {
    val date: Date
        get() = SimpleDateFormat(createdAtPattern, Locale.ENGLISH).parse(value)
}

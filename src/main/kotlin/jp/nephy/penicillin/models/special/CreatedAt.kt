@file:Suppress("UNUSED")

package jp.nephy.penicillin.models.special

import java.text.SimpleDateFormat
import java.util.*

data class CreatedAt(val value: String) {
    companion object {
        private const val pattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    }

    val date: Date
        get() = SimpleDateFormat(pattern, Locale.ENGLISH).parse(value)
}

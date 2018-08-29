package jp.nephy.penicillin.models.special

import java.util.*

data class Language(val value: String) {
    val locale: Locale
        get() = Locale(value)
}

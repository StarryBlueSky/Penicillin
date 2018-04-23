package jp.nephy.penicillin.util

import java.util.*

class Language(val value: String) {
    val locale: Locale
        get() = Locale(value)
}

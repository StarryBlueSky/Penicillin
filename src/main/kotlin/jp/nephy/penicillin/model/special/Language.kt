package jp.nephy.penicillin.model.special

import java.util.*

class Language(val value: String) {
    val locale: Locale
        get() = Locale(value)
}

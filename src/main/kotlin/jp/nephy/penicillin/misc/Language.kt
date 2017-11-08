package jp.nephy.penicillin.misc

import java.util.*

class Language(private val lang: String) {
    @Suppress("UNUSED")
    fun toLocale() = Locale(lang)

    override fun toString() = lang
}

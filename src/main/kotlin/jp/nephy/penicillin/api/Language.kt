package jp.nephy.penicillin.api

import java.util.*

class Language(private val lang: String) {
    fun toLocale() = Locale(lang)

    override fun toString() = lang
}

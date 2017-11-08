package jp.nephy.penicillin.misc

import com.neovisionaries.i18n.CountryCode

class Country(private val code: String) {
    @Suppress("UNUSED")
    fun toCountryCode() = CountryCode.getByCode(code)

    override fun toString() = code
}

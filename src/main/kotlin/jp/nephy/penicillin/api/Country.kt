package jp.nephy.penicillin.api

import com.neovisionaries.i18n.CountryCode

class Country(private val code: String) {
    fun toCountryCode() = CountryCode.getByCode(code)

    override fun toString() = code
}

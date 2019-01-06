@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.models.Account
import jp.nephy.penicillin.models.CommonUser
import jp.nephy.penicillin.models.Status
import java.util.*

val Account.Settings.language: Language
    get() {
        val value by string("language")
        return value.asLanguage()
    }

val Status.lang: Language
    get() {
        val value by string("lang")
        return value.asLanguage()
    }

val CommonUser.lang: Language
    get() {
        val value by string("lang")
        return value.asLanguage()
    }

data class Language(val value: String) {
    val locale: Locale
        get() = Locale(value)
}

fun String.asLanguage(): Language {
    return Language(this)
}

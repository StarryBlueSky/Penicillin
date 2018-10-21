@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byLong
import jp.nephy.jsonkt.delegation.byStringList
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class UserStreamUserWithheld(override val json: ImmutableJsonObject): PenicillinModel {
    private val userWithheld by immutableJsonObject("user_withheld")

    val id by userWithheld.byLong
    val withheldInCountries by userWithheld.byStringList("withheld_in_countries")
}

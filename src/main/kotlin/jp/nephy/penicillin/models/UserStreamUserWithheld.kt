@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byLong
import jp.nephy.jsonkt.delegation.byStringList
import jp.nephy.jsonkt.delegation.jsonObject

data class UserStreamUserWithheld(override val json: JsonObject): PenicillinModel {
    private val userWithheld by jsonObject("user_withheld")
    val id by userWithheld.byLong
    val withheldInCountries by userWithheld.byStringList("withheld_in_countries")
}

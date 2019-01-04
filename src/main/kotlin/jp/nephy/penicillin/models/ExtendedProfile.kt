@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.nullableInt
import jp.nephy.jsonkt.delegation.string

data class ExtendedProfile(override val json: JsonObject): PenicillinModel {
    val id by long
    val idStr by string
    val birthdate by model<BirthDate?>()

    data class BirthDate(override val json: JsonObject): PenicillinModel {
        val year by nullableInt
        val month by nullableInt
        val day by nullableInt
        val visibility by string
        val yearVisibility by string("year_visibility")
    }
}

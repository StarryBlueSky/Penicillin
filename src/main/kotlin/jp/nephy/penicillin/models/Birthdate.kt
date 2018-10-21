@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Birthdate(override val json: ImmutableJsonObject): PenicillinModel {
    val year by nullableInt
    val month by nullableInt
    val day by nullableInt

    val visibility by string
    val yearVisibility by string("year_visibility")
}

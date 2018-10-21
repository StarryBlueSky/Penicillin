@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class PlaceType(override val json: ImmutableJsonObject): PenicillinModel {
    val code by int
    val name by string
}

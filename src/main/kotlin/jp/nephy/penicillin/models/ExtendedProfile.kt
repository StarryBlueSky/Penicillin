@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class ExtendedProfile(override val json: ImmutableJsonObject): PenicillinModel {
    val id by long
    val idStr by string
    val birthdate by model<Birthdate?>()
}

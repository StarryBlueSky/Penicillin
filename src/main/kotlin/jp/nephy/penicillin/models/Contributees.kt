@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Contributees(override val json: ImmutableJsonObject): PenicillinModel {
    val admin by boolean
    val user by model<User>()
}

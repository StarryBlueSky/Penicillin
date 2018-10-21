@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class Error(override val json: ImmutableJsonObject): PenicillinModel {
    val code by int
    val name by nullableString
    val message by string
}

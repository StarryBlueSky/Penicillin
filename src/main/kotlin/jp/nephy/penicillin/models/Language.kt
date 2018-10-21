@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class Language(override val json: ImmutableJsonObject): PenicillinModel {
    val code by string
    val status by string
    val name by string
}

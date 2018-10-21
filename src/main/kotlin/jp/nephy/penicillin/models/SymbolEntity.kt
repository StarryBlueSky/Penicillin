@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class SymbolEntity(override val json: ImmutableJsonObject): PenicillinModel {
    val text by string
    val indices by intList
}


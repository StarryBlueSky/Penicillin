@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class SearchToken(override val json: ImmutableJsonObject): PenicillinModel {
    val token by string
}

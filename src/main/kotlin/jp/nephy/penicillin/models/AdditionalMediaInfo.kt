@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.string

data class AdditionalMediaInfo(override val json: JsonObject): PenicillinModel {
    val title by string
    val description by string
    val embeddable by boolean
}

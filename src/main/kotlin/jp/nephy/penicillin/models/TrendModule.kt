@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class TrendModule(override val json: JsonObject): PenicillinModel {
    val name by string
    val description by nullableString("meta_description")
    val rank by int
    val token by string
    val context by model<TrendContext?>()
    val target by model<TrendTarget>()
}

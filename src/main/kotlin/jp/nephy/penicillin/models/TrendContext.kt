@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendContext(override val json: ImmutableJsonObject): PenicillinModel {
    val relatedQuery by stringList("query")
}

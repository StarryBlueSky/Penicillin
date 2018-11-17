@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class TrendContext(override val json: JsonObject): PenicillinModel {
    val relatedQuery by stringList("query")
}

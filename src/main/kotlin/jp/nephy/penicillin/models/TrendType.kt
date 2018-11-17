@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class TrendType(override val json: JsonObject): PenicillinModel {
    val trend by model<TrendModule?>()
    val promotedTrend by model<PromotedTrendModule?>()
}

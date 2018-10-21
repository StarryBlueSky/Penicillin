@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendType(override val json: ImmutableJsonObject): PenicillinModel {
    val trend by model<TrendModule?>()
    val promotedTrend by model<PromotedTrendModule?>()
}

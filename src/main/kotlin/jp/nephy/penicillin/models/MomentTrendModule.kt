@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class MomentTrendModule(override val json: ImmutableJsonObject): PenicillinModel {
    val metadata by model<TrendMetadata>()
    val trends by modelList<TrendType>()
}

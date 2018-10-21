@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendPlus(override val json: ImmutableJsonObject): PenicillinModel {
    val id by long
    val metadata by model<TrendMetadata>()
    val trends by modelList<TrendType>(key = "modules")
}

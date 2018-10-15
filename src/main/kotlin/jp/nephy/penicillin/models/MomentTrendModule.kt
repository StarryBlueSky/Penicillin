package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class MomentTrendModule(override val json: JsonObject): PenicillinModel {
    val metadata by model<TrendMetadata>()
    val trends by modelList<TrendType>()
}

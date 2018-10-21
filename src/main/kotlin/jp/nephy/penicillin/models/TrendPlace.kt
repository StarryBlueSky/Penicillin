@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendPlace(override val json: ImmutableJsonObject): PenicillinModel {
    val asOf by string("as_of")
    val createdAt by string("created_at")
    val locations by modelList<Location>()
    val trends by modelList<Trend>()
}

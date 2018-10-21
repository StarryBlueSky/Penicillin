@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObject
import jp.nephy.jsonkt.delegation.string

data class GeoQuery(override val json: ImmutableJsonObject): PenicillinModel {
    val params by immutableJsonObject
    val type by string
    val url by string
}

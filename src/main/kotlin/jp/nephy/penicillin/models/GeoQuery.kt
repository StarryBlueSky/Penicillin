@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.jsonObject
import jp.nephy.jsonkt.delegation.string

data class GeoQuery(override val json: JsonObject): PenicillinModel {
    val params by jsonObject
    val type by string
    val url by string
}

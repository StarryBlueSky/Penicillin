@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.nullableString
import jp.nephy.jsonkt.delegation.string

data class TrendArea(override val json: JsonObject): PenicillinModel {
    val country by string
    val countryCode by nullableString // null
    val name by string
    val parentid by int
    val placeType by model<PlaceType>()
    val url by string
    val woeid by int

    data class PlaceType(override val json: JsonObject): PenicillinModel {
        val code by int
        val name by string
    }
}

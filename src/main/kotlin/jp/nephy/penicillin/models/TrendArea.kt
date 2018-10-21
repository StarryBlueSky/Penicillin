@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendArea(override val json: ImmutableJsonObject): PenicillinModel {
    val country by string
    val countryCode by nullableString // null
    val name by string
    val parentid by int
    val placeType by model<PlaceType>()
    val url by string
    val woeid by int
}

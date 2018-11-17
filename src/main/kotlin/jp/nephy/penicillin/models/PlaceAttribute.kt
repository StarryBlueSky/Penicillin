@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class PlaceAttribute(override val json: JsonObject): PenicillinModel {
    val streetAddress by nullableString("street_address")
    val locality by nullableString
    val region by nullableString
    val iso3 by nullableString
    val postalCode by nullableString("postal_code")
    val phone by nullableString
    val twitter by nullableString
    val url by nullableString
    val appId by nullableString("app:id")
    val geotagCount by nullableString
}

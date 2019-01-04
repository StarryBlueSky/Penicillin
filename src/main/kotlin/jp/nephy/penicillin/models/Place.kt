@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Place(override val json: JsonObject): PenicillinModel {
    val attributes by model<Attribute>()
    val boundingBox by model<BoundingBox>(key = "bounding_box")
    val centroid by floatList
    val containedWithin by modelList<Place>(key = "contained_within")
    val country by string
    val countryCode by string(key = "country_code")
    val fullName by string("full_name")
    val geometry by nullableString // null
    val id by string
    val name by string
    val placeType by string("place_type")
    val polylines by jsonArray // []
    val url by string

    data class Attribute(override val json: JsonObject): PenicillinModel {
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

    data class BoundingBox(override val json: JsonObject): PenicillinModel {
        val type by string
        val coordinates by jsonArray
        // [
        //   [
        //     [-77.119759, 38.791645],
        //     [-76.909393, 38.791645],
        //     [-76.909393, 38.995548],
        //     [-77.119759, 38.995548]
        //   ]
        // ]
    }
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Place(override val json: JsonObject): PenicillinModel {
    val attributes by model<PlaceAttribute>()
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
    val polylines by immutableJsonArray // []
    val url by string
}

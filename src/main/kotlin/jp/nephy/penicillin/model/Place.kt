package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.misc.Country

@Suppress("UNUSED")
class Place(override val json: JsonObject): JsonModel {
    val attributes by json.byModel<PlaceAttribute>()
    val boundingBox by json.byModel<BoundingBox>(key = "bounding_box")
    val centroid by json.byFloatList
    val containedWithin by json.byModelList<Place>(key = "contained_within")
    val country by json.byString
    val countryCode by json.byLambda(key = "country_code") { Country(string) }
    val fullName by json.byString("full_name")
    val geometry by json.byNullableString // null
    val id by json.byString
    val name by json.byString
    val placeType by json.byString("place_type")
    val polylines by json.byJsonArray // []
    val url by json.byUrl
}

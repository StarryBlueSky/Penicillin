package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byArray
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.*

class Place(val json: JsonElement) {
    val attributes by json.byPlaceAttribute
    val boundingBox by json.byBoundingBox("bounding_box")
    val centroid by json.byNullableFloatArray
    val containedWithin by json.byPlaceArray("contained_within")
    val country by json.byString
    val countryCode by json.byCountry("country_code")
    val fullName by json.byString("full_name")
    val geometry by json.byNullableString // null
    val id by json.byString
    val name by json.byString
    val placeType by json.byString("place_type")
    val polylines by json.byArray // []
    val url by json.byURL
}

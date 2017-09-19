package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byArray
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.Country
import java.net.URL

class Place(val json: JsonElement) {
    val attributes by json.byModel<PlaceAttribute>()
    val boundingBox by json.byModel<BoundingBox>("bounding_box")
    val centroid by json.byList<Float>()
    val containedWithin by json.byList<Place>("contained_within")
    val country by json.byString
    val countryCode by json.byModel<Country>("country_code")
    val fullName by json.byString("full_name")
    val geometry by json.byNullableString // null
    val id by json.byString
    val name by json.byString
    val placeType by json.byString("place_type")
    val polylines by json.byArray // []
    val url by json.byConverter<String, URL>()
}

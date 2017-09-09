package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement

class GeoId(val json: JsonElement) {
    val attributes by json.byNullableObject // {"geotagCount": "6", "162834:id": "2202"}
    val boundingBox by json.byNullableObject("bounding_box") // {"type": "Polygon", "coordinates": [[[-122.4891333, 37.786925], [-122.4891333, 37.8128675], [-122.446306, 37.8128675], [-122.446306, 37.786925], [-122.4891333, 37.786925]]]}
    val centroid by json.byNullableArray // [-122.46598425785236, 37.79989625]
    val containedWithin by json.byNullableArray("contained_within") // [{"id": "5a110d312052166f", "url": "https://api.twitter.com/1.1/geo/id/5a110d312052166f.json", "place_type": "city", "name": "San Francisco", "full_name": "San Francisco, CA", "country_code": "US", "country": "United States", "centroid": [-122.4461400159226, 37.759828999999996], "bounding_box": {"type": "Polygon", "coordinates": [[[-122.514926, 37.708075], [-122.514926, 37.833238], [-122.357031, 37.833238], [-122.357031, 37.708075], [-122.514926, 37.708075]]]}, "attributes": {}}]
    val country by json.byNullableString // "United States"
    val countryCode by json.byNullableString("country_code") // "US"
    val fullName by json.byNullableString("full_name") // "Presidio, San Francisco"
    val geometry by json.byNullableString // null
    val id by json.byNullableString // "df51dec6f4ee2b2c"
    val name by json.byNullableString // "Presidio"
    val placeType by json.byNullableString("place_type") // "neighborhood"
    val polylines by json.byNullableArray // []
    val url by json.byNullableString // "https://api.twitter.com/1.1/geo/id/df51dec6f4ee2b2c.json"
}

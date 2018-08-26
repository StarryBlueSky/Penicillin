package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.GeoResult
import jp.nephy.penicillin.models.Place


class Geo(override val client: PenicillinClient): Endpoint {
    fun place(placeId: String, vararg options: Pair<String, Any?>)= client.session.getObject<Place>("/geo/id/$placeId.json") {
        query(*options)
    }

    fun reverseGeocode(lat: Float, long: Float, accuracy: String? = null, granularity: String? = null, maxResults: Int? = null, callback: String? = null, vararg options: Pair<String, Any?>)= client.session.getObject<GeoResult>("/geo/reverse_geocode.json") {
        query("lat" to lat, "long" to long, "accuracy" to accuracy, "granularity" to granularity, "max_results" to maxResults, "callback" to callback, *options)
    }

    fun search(lat: Float? = null, long: Float? = null, query: String? = null, ip: String? = null, granularity: String? = null, accuracy: String? = null, maxResults: Int? = null, containedWithin: String? = null, attributeStreetAddress: String? = null, callback: String? = null, vararg options: Pair<String, Any?>)= client.session.getObject<GeoResult>("/geo/search.json") {
        query("lat" to lat, "long" to long, "query" to query, "ip" to ip, "granularity" to granularity, "accuracy" to accuracy, "max_results" to maxResults, "contained_within" to containedWithin, "attribute:street_address" to attributeStreetAddress, "callback" to callback, *options)
    }
}

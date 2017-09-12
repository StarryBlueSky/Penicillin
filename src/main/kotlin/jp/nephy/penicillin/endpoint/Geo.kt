package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.model.Place
import jp.nephy.penicillin.response.ResponseObject
import jp.nephy.penicillin.result.GeoReverseGeocode
import jp.nephy.penicillin.result.GeoSearch

class Geo(private val client: Client) {
    @GET
    fun getPlace(placeId: String, vararg options: Pair<String, String?>): ResponseObject<Place> {
        return client.session.new()
                .url("/geo/id/$placeId.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun reverseGeocode(lat: Float, long: Float, accuracy: String?=null, granularity: String?=null, maxResults: Int?=null, callback: String?=null, vararg options: Pair<String, String?>): ResponseObject<GeoReverseGeocode> {
        return client.session.new()
                .url("/geo/reverse_geocode.json")
                .param("lat" to lat)
                .param("long" to long)
                .param("accuracy" to accuracy)
                .param("granularity" to granularity)
                .param("max_results" to maxResults)
                .param("callback" to callback)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun search(lat: Float?=null, long: Float?=null, query: String?=null, ip: String?=null, granularity: String?=null, accuracy: String?=null, maxResults: Int?=null, containedWithin: String?=null, attributeStreetAddress: String?=null, callback: String?=null, vararg options: Pair<String, String?>): ResponseObject<GeoSearch> {
        return client.session.new()
                .url("/geo/search.json")
                .param("lat" to lat)
                .param("long" to long)
                .param("query" to query)
                .param("ip" to ip)
                .param("granularity" to granularity)
                .param("accuracy" to accuracy)
                .param("max_results" to maxResults)
                .param("contained_within" to containedWithin)
                .param("attribute:street_address" to attributeStreetAddress)
                .param("callback" to callback)
                .params(*options)
                .get()
                .getResponseObject()
    }
}

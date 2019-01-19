/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.models.GeoResult
import jp.nephy.penicillin.models.Place

val PenicillinClient.geo: Geo
    get() = Geo(this)

class Geo(override val client: PenicillinClient): Endpoint {
    fun place(placeId: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/geo/id/$placeId.json") {
        parameter(*options)
    }.jsonObject<Place>()

    fun reverseGeocode(lat: Float, long: Float, accuracy: String? = null, granularity: String? = null, maxResults: Int? = null, callback: String? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/geo/reverse_geocode.json") {
            parameter("lat" to lat, "long" to long, "accuracy" to accuracy, "granularity" to granularity, "max_results" to maxResults, "callback" to callback, *options)
        }.jsonObject<GeoResult>()

    fun search(
        lat: Float? = null,
        long: Float? = null,
        query: String? = null,
        ip: String? = null,
        granularity: String? = null,
        accuracy: String? = null,
        maxResults: Int? = null,
        containedWithin: String? = null,
        attributeStreetAddress: String? = null,
        callback: String? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/geo/search.json") {
        parameter(
            "lat" to lat,
            "long" to long,
            "query" to query,
            "ip" to ip,
            "granularity" to granularity,
            "accuracy" to accuracy,
            "max_results" to maxResults,
            "contained_within" to containedWithin,
            "attribute:street_address" to attributeStreetAddress,
            "callback" to callback,
            *options
        )
    }.jsonObject<GeoResult>()
}

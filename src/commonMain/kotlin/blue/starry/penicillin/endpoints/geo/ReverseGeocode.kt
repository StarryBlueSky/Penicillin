/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.endpoints.geo

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Geo
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.GeoResult

/**
 * Given a latitude and a longitude, searches for up to 20 places that can be used as a place_id when updating a status.
 * This request is an informative call and will deliver generalized results about geography.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/geo/places-near-location/api-reference/get-geo-reverse_geocode)
 *
 * @param latitude The latitude to search around. This parameter will be ignored unless it is inside the range -90.0 to +90.0 (North is positive) inclusive. It will also be ignored if there isn't a corresponding long parameter.
 * @param longitude The longitude to search around. The valid ranges for longitude are -180.0 to +180.0 (East is positive) inclusive. This parameter will be ignored if outside that range, if it is not a number, if geo_enabled is disabled, or if there not a corresponding lat parameter.
 * @param accuracy A hint on the "region" in which to search. If a number, then this is a radius in meters, but it can also take a string that is suffixed with ft to specify feet. If this is not passed in, then it is assumed to be 0m. If coming from a device, in practice, this value is whatever accuracy the device has measuring its location (whether it be coming from a GPS, WiFi triangulation, etc.).
 * @param granularity This is the minimal granularity of place types to return and must be one of: neighborhood, city, admin or country . If no granularity is provided for the request neighborhood is assumed. Setting this to city, for example, will find places which have a type of city, admin or country.
 * @param maxResults A hint as to the number of results to return. This does not guarantee that the number of results returned will equal max_results, but instead informs how many "nearby" results to return. Ideally, only pass in the number of places you intend to display to the user here.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Geo] endpoint instance.
 * @return [JsonObjectApiAction] for [GeoResult] model.
 */
public fun Geo.reverseGeocode(
    latitude: Double,
    longitude: Double,
    accuracy: String? = null,
    granularity: GeoGranularity = GeoGranularity.Default,
    maxResults: Int? = null,
    vararg options: Option
): JsonObjectApiAction<GeoResult> = client.session.get("/1.1/geo/reverse_geocode.json") {
    parameters(
        "lat" to latitude,
        "long" to longitude,
        "accuracy" to accuracy,
        "granularity" to granularity,
        "max_results" to maxResults,
        *options
    )
}.jsonObject { GeoResult(it, client) }

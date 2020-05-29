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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.geo

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Geo
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.GeoResult

/**
 * Search for places that can be attached to a Tweet via [POST statuses/update](https://developer.twitter.com/en/docs/tweets/post-and-engage/post-statuses-update). Given a latitude and a longitude pair, an IP address, or a name, this request will return a list of all the valid places that can be used as the place_id when updating a status.
 * Conceptually, a query can be made from the user's location, retrieve a list of places, have the user validate the location they are at, and then send the ID of this location with a call to [POST statuses/update](https://developer.twitter.com/en/docs/tweets/post-and-engage/post-statuses-update).
 * This is the recommended method to use find places that can be attached to statuses/update. Unlike [GET geo/reverse_geocode](https://developer.twitter.com/en/docs/geo/places-near-location/api-reference/get-geo-reverse_geocode) which provides raw data access, this endpoint can potentially re-order places with regards to the user who is authenticated. This approach is also preferred for interactive place matching with the user.
 * Some parameters in this method are only required based on the existence of other parameters. For instance, "lat" is required if "long" is provided, and vice-versa. Authentication is recommended, but not required with this method.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/geo/places-near-location/api-reference/get-geo-reverse_geocode)
 *
 * @param latitude The latitude to search around. This parameter will be ignored unless it is inside the range -90.0 to +90.0 (North is positive) inclusive. It will also be ignored if there isn't a corresponding long parameter.
 * @param longitude The longitude to search around. The valid ranges for longitude are -180.0 to +180.0 (East is positive) inclusive. This parameter will be ignored if outside that range, if it is not a number, if geo_enabled is disabled, or if there not a corresponding lat parameter.
 * @param query Free-form text to match against while executing a geo-based query, best suited for finding nearby locations by name. Remember to URL encode the query.
 * @param ip An IP address. Used when attempting to fix geolocation based off of the user's IP address.
 * @param granularity This is the minimal granularity of place types to return and must be one of: neighborhood, city, admin or country . If no granularity is provided for the request neighborhood is assumed. Setting this to city, for example, will find places which have a type of city, admin or country.
 * @param accuracy A hint on the "region" in which to search. If a number, then this is a radius in meters, but it can also take a string that is suffixed with ft to specify feet. If this is not passed in, then it is assumed to be 0m. If coming from a device, in practice, this value is whatever accuracy the device has measuring its location (whether it be coming from a GPS, WiFi triangulation, etc.).
 * @param maxResults A hint as to the number of results to return. This does not guarantee that the number of results returned will equal max_results, but instead informs how many "nearby" results to return. Ideally, only pass in the number of places you intend to display to the user here.
 * @param containedWithin This is the place_id which you would like to restrict the search results to. Setting this value means only places within the given place_id will be found. Specify a place_id. For example, to scope all results to places within "San Francisco, CA USA", you would specify a place_id of "5a110d312052166f".
 * @param attributeStreetAddress This parameter searches for places which have this given street address. There are other well-known, and application specific attributes available. Custom attributes are also permitted. Learn more about [Place attributes](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/geo-objects).
 * @param options Optional. Custom parameters of this request.
 * @receiver [Geo] endpoint instance.
 * @return [JsonObjectApiAction] for [GeoResult] model.
 */
fun Geo.search(
    latitude: Double? = null,
    longitude: Double? = null,
    query: String? = null,
    ip: String? = null,
    granularity: GeoGranularity? = null,
    accuracy: String? = null,
    maxResults: Int? = null,
    containedWithin: String? = null,
    attributeStreetAddress: String? = null,
    callback: String? = null,
    vararg options: Option
) = client.session.get("/1.1/geo/search.json") {
    parameters(
        "lat" to latitude,
        "long" to longitude,
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
}.jsonObject { GeoResult(it, client) }

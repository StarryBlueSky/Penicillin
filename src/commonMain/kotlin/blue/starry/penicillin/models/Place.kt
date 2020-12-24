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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.JsonArray


public data class Place(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val attributes: Attribute by model { Attribute(it, client) }
    public val boundingBox: BoundingBox by model("bounding_box") { BoundingBox(it, client) }
    public val centroid: List<Float> by floatList
    public val containedWithin: List<Place> by modelList("contained_within") { Place(it, client) }
    public val country: String by string
    public val countryCode: String by string("country_code")
    public val fullName: String by string("full_name")
    public val geometry: String? by nullableString // null
    public val id: String by string
    public val name: String by string
    public val placeType: String by string("place_type")
    public val polylines: JsonArray by jsonArray // []
    public val url: String by string

    public data class Attribute(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val streetAddress: String? by nullableString("street_address")
        public val locality: String? by nullableString
        public val region: String? by nullableString
        public val iso3: String? by nullableString
        public val postalCode: String? by nullableString("postal_code")
        public val phone: String? by nullableString
        public val twitter: String? by nullableString
        public val url: String? by nullableString
        public val appId: String? by nullableString("app:id")
        public val geotagCount: String? by nullableString
    }

    public data class BoundingBox(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val type: String by string
        public val coordinates: JsonArray by jsonArray
        // [
        //   [
        //     [-77.119759, 38.791645],
        //     [-76.909393, 38.791645],
        //     [-76.909393, 38.995548],
        //     [-77.119759, 38.995548]
        //   ]
        // ]
    }
}

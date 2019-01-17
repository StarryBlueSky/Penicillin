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

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Place(override val json: JsonObject): PenicillinModel {
    val attributes by model<Attribute>()
    val boundingBox by model<BoundingBox>(key = "bounding_box")
    val centroid by floatList
    val containedWithin by modelList<Place>(key = "contained_within")
    val country by string
    val countryCode by string(key = "country_code")
    val fullName by string("full_name")
    val geometry by nullableString // null
    val id by string
    val name by string
    val placeType by string("place_type")
    val polylines by jsonArray // []
    val url by string

    data class Attribute(override val json: JsonObject): PenicillinModel {
        val streetAddress by nullableString("street_address")
        val locality by nullableString
        val region by nullableString
        val iso3 by nullableString
        val postalCode by nullableString("postal_code")
        val phone by nullableString
        val twitter by nullableString
        val url by nullableString
        val appId by nullableString("app:id")
        val geotagCount by nullableString
    }

    data class BoundingBox(override val json: JsonObject): PenicillinModel {
        val type by string
        val coordinates by jsonArray
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

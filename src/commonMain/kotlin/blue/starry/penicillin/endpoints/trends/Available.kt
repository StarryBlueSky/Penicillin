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

package blue.starry.penicillin.endpoints.trends

import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Trends
import blue.starry.penicillin.models.TrendArea

/**
 * Returns the locations that Twitter has trending topic information for.
 * The response is an array of "locations" that encode the location's WOEID and some other human-readable information such as a canonical name and country the location belongs in.
 * A WOEID is a [Yahoo! Where On Earth ID](http://developer.yahoo.com/geo/geoplanet/).
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/trends/locations-with-trending-topics/api-reference/get-trends-available)
 * 
 * @param options Optional. Custom parameters of this request.
 * @receiver [Trends] endpoint instance.
 * @return [JsonArrayApiAction] for [TrendArea] model.
 */
public fun Trends.availableAreas(
    vararg options: Option
): JsonArrayApiAction<TrendArea> = client.session.get("/1.1/trends/available.json") {
    parameters(*options)
}.jsonArray { TrendArea(it, client) }

 /**
 * Shorthand property to [Trends.availableAreas].
 * @see Trends.availableAreas
 */
public val Trends.availableAreas: JsonArrayApiAction<TrendArea>
     get() = availableAreas()

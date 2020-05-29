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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.collections.entries

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.CollectionEntries
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.Collection

/**
 * Retrieve the identified Collection, presented as a list of the Tweets curated within.
 * The response structure of this method differs significantly from timelines you may be used to working with elsewhere in the Twitter API.
 * To navigate a Collection, use the position object of a response, which includes attributes for max_position, min_position, and was_truncated. was_truncated indicates whether additional Tweets exist in the collection outside of the range of the current request. To retrieve Tweets further back in time, use the value of min_position found in the current response as the max_position parameter in the next call to this endpoint.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/get-collections-entries)
 * 
 * @param id The identifier of the Collection for which to return results.
 * @param count Specifies the maximum number of results to include in the response. Specify a count between 1 and 200. A next_cursor value will be provided in the response if additional results are available.
 * @param maxPosition Returns results with a position value less than or equal to the specified position.
 * @param minPosition    Returns results with a position greater than the specified position.
 * @param options Optional. Custom parameters of this request.
 * @receiver [CollectionEntries] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.Entry.Result] model.
 */
fun CollectionEntries.entries(
    id: String,
    count: Int? = null,
    maxPosition: Int? = null,
    minPosition: Int? = null,
    vararg options: Option
) = client.session.get("/1.1/collections/entries.json") {
    parameters(
        "id" to id,
        "count" to count,
        "max_position" to maxPosition,
        "min_position" to minPosition,
        *options
    )
}.jsonObject { Collection.Entry.Result(it, client) }

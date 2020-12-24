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

package blue.starry.penicillin.endpoints.collections.entries

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.CollectionEntries
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Collection

/**
 * Move a specified Tweet to a new position in a curation_reverse_chron ordered collection.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/post-collections-entries-move)
 * 
 * @param id The identifier of the Collection receiving the Tweet.
 * @param tweetId The identifier of the Tweet to add to the Collection.
 * @param relativeTo The identifier of the Tweet used for relative positioning.
 * @param above Set to false to insert the specified tweet_id below the relative_to Tweet in the collection. Default: true
 * @param options Optional. Custom parameters of this request.
 * @receiver [CollectionEntries] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.Entry.Result] model.
 */
public fun CollectionEntries.move(
    id: String,
    tweetId: Long,
    relativeTo: Long,
    above: Boolean? = null,
    vararg options: Option
): JsonObjectApiAction<Collection.Entry.Result> = client.session.post("/1.1/collections/entries/move.json") {
    formBody(
        "id" to id,
        "tweet_id" to tweetId,
        "relative_to" to relativeTo,
        "above" to above,
        *options
    )

}.jsonObject { Collection.Entry.Result(it, client) }

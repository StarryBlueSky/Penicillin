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

package blue.starry.penicillin.endpoints.statuses

import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Statuses
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.models.Status

/**
 * Returns fully-hydrated [Tweet objects](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) for up to 100 Tweets per request, as specified by comma-separated values passed to the id parameter.
 * This method is especially useful to get the details (hydrate) a collection of Tweet IDs.
 * [GET statuses/show/:id](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-show-id) is used to retrieve a single Tweet object.
 * 
 * There are a few things to note when using this method.
 * - You must be following a protected user to be able to see their most recent Tweets. If you don't follow a protected user their status will be removed.
 * - The order of Tweet IDs may not match the order of Tweets in the returned array.
 * - If a requested Tweet is unknown or deleted, then that Tweet will not be returned in the results list, unless the map parameter is set to true, in which case it will be returned with a value of null.
 * - If none of your lookup criteria matches valid Tweet IDs an empty array will be returned for map=false.
 * - You are strongly encouraged to use a POST for larger requests.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-lookup)
 * 
 * @param ids A list of Tweet IDs, up to 100 are allowed in a single request.
 * @param includeEntities The entities node that may appear within embedded statuses will not be included when set to false.
 * @param trimUser When set to either true , t or 1 , each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param map When using the map parameter, Tweets that do not exist or cannot be viewed by the current user will still have their key represented but with an explicitly null value paired with it.
 * @param includeExtAltText If alt text has been added to any attached media entities, this parameter will return an ext_alt_text value in the top-level key for the media entity. If no value has been set, this will be returned as null.
 * @param includeCardUri When set to either true , t or 1 , each Tweet returned will include a card_uri attribute when there is an ads card attached to the Tweet and when that card was attached using the card_uri value.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Statuses] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 */
fun Statuses.lookup(
    ids: List<Long>,
    includeEntities: Boolean? = null,
    trimUser: Boolean? = null,
    map: Boolean? = null,
    includeExtAltText: Boolean? = null,
    includeCardUri: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
) = client.session.get("/1.1/statuses/lookup.json") {
    parameters(
        "id" to ids.joinToString(","),
        "include_entities" to includeEntities,
        "trim_user" to trimUser,
        "map" to map,
        "include_ext_alt_text" to includeExtAltText,
        "include_card_uri" to includeCardUri,
        "tweet_mode" to tweetMode,
        *options
    )
}.jsonArray { Status(it, client) }

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

package blue.starry.penicillin.endpoints.search


import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Search
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.extensions.toYYYYMMdd
import kotlinx.datetime.LocalDate

private typealias SearchModel = blue.starry.penicillin.models.Search

/**
 * Returns a collection of relevant [Tweets](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) matching a specified query.
 * Please note that Twitter's search service and, by extension, the Search API is not meant to be an exhaustive source of Tweets. Not all Tweets will be indexed or made available via the search interface.
 * To learn how to use [Twitter Search](https://twitter.com/search) effectively, please see the [Standard search operators](https://developer.twitter.com/en/docs/tweets/search/guides/standard-operators) page for a list of available filter operators. Also, see the [Working with Timelines](https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines) page to learn best practices for navigating results by since_id and max_id.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets)
 * 
 * @param query A UTF-8, URL-encoded search query of 500 characters maximum, including operators. Queries may additionally be limited by complexity.
 * @param geocode Returns tweets by users located within a given radius of the given latitude/longitude. The location is preferentially taking from the Geotagging API, but will fall back to their Twitter profile. The parameter value is specified by " latitude,longitude,radius ", where radius units must be specified as either " mi " (miles) or " km " (kilometers). Note that you cannot use the near operator via the API to geocode arbitrary locations; however you can use this geocode parameter to search near geocodes directly. A maximum of 1,000 distinct "sub-regions" will be considered when using the radius modifier.
 * @param lang Restricts tweets to the given language, given by an [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) code. Language detection is best-effort.
 * @param locale Specify the language of the query you are sending (only ja is currently effective). This is intended for language-specific consumers and the default should work in the majority of cases.
 * @param resultType Optional. Specifies what type of search results you would prefer to receive. The current default is "mixed." Valid values include.
 * @param count The number of tweets to return per page, up to a maximum of 100. Defaults to 15. This was formerly the "rpp" parameter in the old Search API.
 * @param until Returns tweets created before the given date. Date should be formatted as YYYY-MM-DD. Keep in mind that the search index has a 7-day limit. In other words, no tweets will be found for a date older than one week.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param includeEntities The entities node will not be included when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Search] endpoint instance.
 * @return [JsonObjectApiAction] for [SearchModel] model.
 */
public fun Search.search(
    query: String,
    geocode: String? = null,
    lang: String? = null,
    locale: String? = null,
    resultType: SearchResultType = SearchResultType.Default,
    count: Int? = null,
    until: LocalDate? = null,
    sinceId: Long? = null,
    maxId: Long? = null,
    includeEntities: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
): JsonObjectApiAction<SearchModel /* = blue.starry.penicillin.models.Search */> = client.session.get("/1.1/search/tweets.json") {
    parameters(
        "q" to query,
        "geocode" to geocode,
        "lang" to lang,
        "locale" to locale,
        "result_type" to resultType,
        "count" to count,
        "until" to until?.toYYYYMMdd(),
        "since_id" to sinceId,
        "max_id" to maxId,
        "include_entities" to includeEntities,
        "tweet_mode" to tweetMode,
        *options
    )
}.jsonObject { SearchModel(it, client) }

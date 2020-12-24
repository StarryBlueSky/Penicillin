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

package blue.starry.penicillin.endpoints.statuses

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Statuses
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.models.Status

/**
 * Returns a single [Tweet](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object), specified by the id parameter. The Tweet's author will also be embedded within the Tweet.
 * See [GET statuses/lookup](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-lookup) for getting Tweets in bulk (up to 100 per call). See also [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines), [Embedded Tweets](https://developer.twitter.com/web/embedded-tweets), and [GET statuses/oembed](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-oembed) for tools to render Tweets according to [Display Requirements](https://about.twitter.com/company/display-requirements).
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-show-id)
 * 
 * @param id The numerical ID of the desired Tweet.
 * @param trimUser When set to either true , t or 1 , each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param includeMyRetweet When set to either true, t or 1, any Tweets returned that have been retweeted by the authenticating user will include an additional current_user_retweet node, containing the ID of the source status for the retweet.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeExtAltText If alt text has been added to any attached media entities, this parameter will return an ext_alt_text value in the top-level key for the media entity. If no value has been set, this will be returned as null.
 * @param includeCardUri When set to either true, t or 1, the retrieved Tweet will include a card_uri attribute when there is an ads card attached to the Tweet and when that card was attached using the card_uri value.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Statuses] endpoint instance.
 * @return [JsonObjectApiAction] for [Status] model.
 */
public fun Statuses.show(
    id: Long,
    trimUser: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    includeEntities: Boolean? = null,
    includeExtAltText: Boolean? = null,
    includeCardUri: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
): JsonObjectApiAction<Status> = client.session.get("/1.1/statuses/show.json") {
    parameters(
        "id" to id,
        "trim_user" to trimUser,
        "include_my_retweet" to includeMyRetweet,
        "include_entities" to includeEntities,
        "include_ext_alt_text" to includeExtAltText,
        "include_card_uri" to includeCardUri,
        "tweet_mode" to tweetMode,
        *options
    )
}.jsonObject { Status(it, client) }

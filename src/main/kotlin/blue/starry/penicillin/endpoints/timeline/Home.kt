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

package blue.starry.penicillin.endpoints.timeline

import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Timeline
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.models.Status

/**
 * Returns a collection of the most recent [Tweets](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) and Retweets posted by the authenticating user and the users they follow. The home timeline is central to how most users interact with the Twitter service.
 * Up to 800 Tweets are obtainable on the home timeline. It is more volatile for users that follow many users or follow users who Tweet frequently.
 * See [Working with Timelines](https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines) for instructions on traversing timelines efficiently.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-home_timeline.html)
 *
 * @param count Specifies the number of records to retrieve. Must be less than or equal to 200. Defaults to 20. The value of count is best thought of as a limit to the number of tweets to return because suspended or deleted content is removed after the count has been applied.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occurred since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param trimUser When set to either true, t or 1, each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param excludeReplies This parameter will prevent replies from appearing in the returned timeline. Using exclude_replies with the count parameter will mean you will receive up-to count Tweets — this is because the count parameter retrieves that many Tweets before filtering out retweets and replies.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeRTs When set to false , the timeline will strip any native retweets (though they will still count toward both the maximal length of the timeline and the slice selected by the count parameter). Note: If you're using the trim_user parameter in conjunction with include_rts, the retweets will still contain a full user object.
 * @param includeMyRetweet Not documented yet.
 * @param tweetMode Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 */
fun Timeline.homeTimeline(
    count: Int? = null,
    sinceId: Long? = null,
    maxId: Long? = null,
    trimUser: Boolean? = null,
    excludeReplies: Boolean? = null,
    includeEntities: Boolean? = null,
    includeRTs: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/statuses/home_timeline.json") {
    parameters(
        "count" to count,
        "since_id" to sinceId,
        "max_id" to maxId,
        "trim_user" to trimUser,
        "exclude_replies" to excludeReplies,
        "include_entities" to includeEntities,
        "tweet_mode" to tweetMode,
        "include_rts" to includeRTs,
        "include_my_retweet" to includeMyRetweet,
        "include_card_uri" to includeCardUri,
        *options
    )
}.jsonArray { Status(it, client) }

/**
 * Shorthand property to [Timeline.homeTimeline].
 * @see Timeline.homeTimeline
 */
val Timeline.homeTimeline
    get() = homeTimeline()

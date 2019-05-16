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

package jp.nephy.penicillin.endpoints.timeline

import jp.nephy.penicillin.core.request.action.JsonArrayApiAction
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Timeline
import jp.nephy.penicillin.endpoints.common.TweetMode
import jp.nephy.penicillin.models.Status

/**
 * Returns a collection of the most recent [Tweets](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) posted by the [user](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) indicated by the screen_name or user_id parameters.
 * User timelines belonging to protected users may only be requested when the authenticated user either "owns" the timeline or is an approved follower of the owner.
 * The timeline returned is the equivalent of the one seen as a user's profile on Twitter.
 * This method can only return up to 3,200 of a user's most recent Tweets. Native retweets of other statuses by the user is included in this total, regardless of whether include_rts is set to false when requesting this resource.
 * See [Working with Timelines](https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines) for instructions on traversing timelines.
 * See [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines), [Embedded Tweets](https://developer.twitter.com/web/embedded-tweets), and [GET statuses/oembed](https://developer.twitter.com/rest/reference/get/statuses/oembed) for tools to render Tweets according to [Display Requirements](https://about.twitter.com/company/display-requirements).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-user_timeline)
 *
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets that can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param count Specifies the number of Tweets to try and retrieve, up to a maximum of 200 per distinct request. The value of count is best thought of as a limit to the number of Tweets to return because suspended or deleted content is removed after the count has been applied. We include retweets in the count, even if include_rts is not supplied. It is recommended you always send include_rts=1 when using this API method.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param trimUser When set to either true, t or 1, each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param excludeReplies This parameter will prevent replies from appearing in the returned timeline. Using exclude_replies with the count parameter will mean you will receive up-to count tweets — this is because the count parameter retrieves that many Tweets before filtering out retweets and replies.
 * @param includeRTs When set to false , the timeline will strip any native retweets (though they will still count toward both the maximal length of the timeline and the slice selected by the count parameter). Note: If you're using the trim_user parameter in conjunction with include_rts, the retweets will still contain a full user object.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeMyRetweet Not documented yet.
 * @param tweetMode Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 */
fun Timeline.userTimeline(
    sinceId: Long? = null,
    count: Int? = null,
    maxId: Long? = null,
    trimUser: Boolean? = null,
    excludeReplies: Boolean? = null,
    includeRTs: Boolean? = null,
    includeEntities: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = userTimelineInternal(null, null, sinceId, count, maxId, trimUser, excludeReplies, includeRTs, includeEntities, includeMyRetweet, tweetMode, includeCardUri, *options)

/**
 * Returns a collection of the most recent [Tweets](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) posted by the [user](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) indicated by the screen_name or user_id parameters.
 * User timelines belonging to protected users may only be requested when the authenticated user either "owns" the timeline or is an approved follower of the owner.
 * The timeline returned is the equivalent of the one seen as a user's profile on Twitter.
 * This method can only return up to 3,200 of a user's most recent Tweets. Native retweets of other statuses by the user is included in this total, regardless of whether include_rts is set to false when requesting this resource.
 * See [Working with Timelines](https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines) for instructions on traversing timelines.
 * See [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines), [Embedded Tweets](https://developer.twitter.com/web/embedded-tweets), and [GET statuses/oembed](https://developer.twitter.com/rest/reference/get/statuses/oembed) for tools to render Tweets according to [Display Requirements](https://about.twitter.com/company/display-requirements).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-user_timeline)
 *
 * @param userId The ID of the user for whom to return results.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets that can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param count Specifies the number of Tweets to try and retrieve, up to a maximum of 200 per distinct request. The value of count is best thought of as a limit to the number of Tweets to return because suspended or deleted content is removed after the count has been applied. We include retweets in the count, even if include_rts is not supplied. It is recommended you always send include_rts=1 when using this API method.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param trimUser When set to either true, t or 1, each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param excludeReplies This parameter will prevent replies from appearing in the returned timeline. Using exclude_replies with the count parameter will mean you will receive up-to count tweets — this is because the count parameter retrieves that many Tweets before filtering out retweets and replies.
 * @param includeRTs When set to false , the timeline will strip any native retweets (though they will still count toward both the maximal length of the timeline and the slice selected by the count parameter). Note: If you're using the trim_user parameter in conjunction with include_rts, the retweets will still contain a full user object.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeMyRetweet Not documented yet.
 * @param tweetMode Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 */
fun Timeline.userTimelineByUserId(
    userId: Long,
    sinceId: Long? = null,
    count: Int? = null,
    maxId: Long? = null,
    trimUser: Boolean? = null,
    excludeReplies: Boolean? = null,
    includeRTs: Boolean? = null,
    includeEntities: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = userTimelineInternal(userId, null, sinceId, count, maxId, trimUser, excludeReplies, includeRTs, includeEntities, includeMyRetweet, tweetMode, includeCardUri, *options)

/**
 * Returns a collection of the most recent [Tweets](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object) posted by the [user](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object) indicated by the screen_name or user_id parameters.
 * User timelines belonging to protected users may only be requested when the authenticated user either "owns" the timeline or is an approved follower of the owner.
 * The timeline returned is the equivalent of the one seen as a user's profile on Twitter.
 * This method can only return up to 3,200 of a user's most recent Tweets. Native retweets of other statuses by the user is included in this total, regardless of whether include_rts is set to false when requesting this resource.
 * See [Working with Timelines](https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines) for instructions on traversing timelines.
 * See [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines), [Embedded Tweets](https://developer.twitter.com/web/embedded-tweets), and [GET statuses/oembed](https://developer.twitter.com/rest/reference/get/statuses/oembed) for tools to render Tweets according to [Display Requirements](https://about.twitter.com/company/display-requirements).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-user_timeline)
 *
 * @param screenName The screen name of the user for whom to return results.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets that can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param count Specifies the number of Tweets to try and retrieve, up to a maximum of 200 per distinct request. The value of count is best thought of as a limit to the number of Tweets to return because suspended or deleted content is removed after the count has been applied. We include retweets in the count, even if include_rts is not supplied. It is recommended you always send include_rts=1 when using this API method.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param trimUser When set to either true, t or 1, each Tweet returned in a timeline will include a user object including only the status authors numerical ID. Omit this parameter to receive the complete user object.
 * @param excludeReplies This parameter will prevent replies from appearing in the returned timeline. Using exclude_replies with the count parameter will mean you will receive up-to count tweets — this is because the count parameter retrieves that many Tweets before filtering out retweets and replies.
 * @param includeRTs When set to false , the timeline will strip any native retweets (though they will still count toward both the maximal length of the timeline and the slice selected by the count parameter). Note: If you're using the trim_user parameter in conjunction with include_rts, the retweets will still contain a full user object.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeMyRetweet Not documented yet.
 * @param tweetMode Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 */
fun Timeline.userTimelineByScreenName(
    screenName: String,
    sinceId: Long? = null,
    count: Int? = null,
    maxId: Long? = null,
    trimUser: Boolean? = null,
    excludeReplies: Boolean? = null,
    includeRTs: Boolean? = null,
    includeEntities: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = userTimelineInternal(null, screenName, sinceId, count, maxId, trimUser, excludeReplies, includeRTs, includeEntities, includeMyRetweet, tweetMode, includeCardUri, *options)

private fun Timeline.userTimelineInternal(
    userId: Long? = null,
    screenName: String? = null,
    sinceId: Long? = null,
    count: Int? = null,
    maxId: Long? = null,
    trimUser: Boolean? = null,
    excludeReplies: Boolean? = null,
    includeRTs: Boolean? = null,
    includeEntities: Boolean? = null,
    includeMyRetweet: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/statuses/user_timeline.json") {
    parameter(
        "user_id" to userId,
        "screen_name" to screenName,
        "since_id" to sinceId,
        "count" to count,
        "max_id" to maxId,
        "trim_user" to trimUser,
        "exclude_replies" to excludeReplies,
        "include_rts" to includeRTs,
        "include_entities" to includeEntities,
        "include_my_retweet" to includeMyRetweet,
        "tweet_mode" to tweetMode.value,
        "include_card_uri" to includeCardUri,
        *options
    )
}.jsonArray<Status>()

/**
 * Shorthand property to [Timeline.userTimeline].
 * @see Timeline.userTimeline
 */
val Timeline.userTimeline
    get() = userTimeline()

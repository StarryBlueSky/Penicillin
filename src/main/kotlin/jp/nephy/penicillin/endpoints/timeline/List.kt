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
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Timeline
import jp.nephy.penicillin.endpoints.common.TweetMode
import jp.nephy.penicillin.models.Status

/**
 * Returns a timeline of tweets authored by members of the specified list. Retweets are included by default. Use the include_rts=false parameter to omit retweets.
 * [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines) is a great way to embed list timelines on your website.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-statuses)
 * 
 * @param listId The numerical id of the list.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param count Specifies the number of results to retrieve per "page."
 * @param includeEntities Entities are ON by default in API 1.1, each tweet includes a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. You can omit entities from the result by using include_entities=false
 * @param includeRTs When set to either true, t or 1, the list timeline will contain native retweets (if they exist) in addition to the standard stream of tweets. The output format of retweeted tweets is identical to the representation you see in home_timeline.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 * @see listTimelineByOwnerScreenName
 * @see listTimelineByOwnerId
 */
fun Timeline.listTimeline(
    listId: Long,
    sinceId: Long? = null,
    maxId: Long? = null,
    count: Int? = null,
    includeEntities: Boolean? = null,
    includeRTs: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeMyRetweet: Boolean? = null,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = listTimeline(listId, null, null, null, sinceId, maxId, count, includeEntities, includeRTs, tweetMode, includeMyRetweet, includeCardUri, *options)

/**
 * Returns a timeline of tweets authored by members of the specified list. Retweets are included by default. Use the include_rts=false parameter to omit retweets.
 * [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines) is a great way to embed list timelines on your website.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-statuses)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param count Specifies the number of results to retrieve per "page."
 * @param includeEntities Entities are ON by default in API 1.1, each tweet includes a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. You can omit entities from the result by using include_entities=false
 * @param includeRTs When set to either true, t or 1, the list timeline will contain native retweets (if they exist) in addition to the standard stream of tweets. The output format of retweeted tweets is identical to the representation you see in home_timeline.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 * @see listTimeline
 * @see listTimelineByOwnerId
 */
fun Timeline.listTimelineByOwnerScreenName(
    slug: String,
    ownerScreenName: String,
    sinceId: Long? = null,
    maxId: Long? = null,
    count: Int? = null,
    includeEntities: Boolean? = null,
    includeRTs: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeMyRetweet: Boolean? = null,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = listTimeline(null, slug, ownerScreenName, null, sinceId, maxId, count, includeEntities, includeRTs, tweetMode, includeMyRetweet, includeCardUri, *options)

/**
 * Returns a timeline of tweets authored by members of the specified list. Retweets are included by default. Use the include_rts=false parameter to omit retweets.
 * [Embedded Timelines](https://developer.twitter.com/web/embedded-timelines) is a great way to embed list timelines on your website.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-statuses)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param count Specifies the number of results to retrieve per "page."
 * @param includeEntities Entities are ON by default in API 1.1, each tweet includes a node called "entities". This node offers a variety of metadata about the tweet in a discreet structure, including: user_mentions, urls, and hashtags. You can omit entities from the result by using include_entities=false
 * @param includeRTs When set to either true, t or 1, the list timeline will contain native retweets (if they exist) in addition to the standard stream of tweets. The output format of retweeted tweets is identical to the representation you see in home_timeline.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Timeline] endpoint instance.
 * @return [JsonArrayApiAction] for [Status] model.
 * @see listTimeline
 * @see listTimelineByOwnerScreenName
 */
fun Timeline.listTimelineByOwnerId(
    slug: String,
    ownerId: Long,
    sinceId: Long? = null,
    maxId: Long? = null,
    count: Int? = null,
    includeEntities: Boolean? = null,
    includeRTs: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeMyRetweet: Boolean? = null,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = listTimeline(null, slug, null, ownerId, sinceId, maxId, count, includeEntities, includeRTs, tweetMode, includeMyRetweet, includeCardUri, *options)

private fun Timeline.listTimeline(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    sinceId: Long? = null,
    maxId: Long? = null,
    count: Int? = null,
    includeEntities: Boolean? = null,
    includeRTs: Boolean? = null,
    tweetMode: TweetMode = TweetMode.Default,
    includeMyRetweet: Boolean? = null,
    includeCardUri: Boolean? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/statuses.json") {
    parameters(
        "list_id" to listId,
        "slug" to slug,
        "owner_screen_name" to ownerScreenName,
        "owner_id" to ownerId,
        "since_id" to sinceId,
        "max_id" to maxId,
        "count" to count,
        "include_entities" to includeEntities,
        "include_rts" to includeRTs,
        "tweet_mode" to tweetMode.value,
        "include_my_retweet" to includeMyRetweet,
        "include_card_uri" to includeCardUri,
        *options
    )
}.jsonArray<Status>()

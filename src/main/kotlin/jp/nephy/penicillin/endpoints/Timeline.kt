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

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.models.Status

val ApiClient.timeline: Timeline
    get() = Timeline(this)

class Timeline(override val client: ApiClient): Endpoint {
    fun home(
        count: Int? = null,
        sinceId: Long? = null,
        maxId: Long? = null,
        trimUser: Boolean? = null,
        excludeReplies: Boolean? = null,
        includeEntities: Boolean? = null,
        includeRTs: Boolean? = null,
        includeMyRetweet: Boolean? = null,
        tweetMode: String? = null,
        vararg options: Option
    ) = client.session.get("/1.1/statuses/home_timeline.json") {
        parameter(
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "trim_user" to trimUser,
            "exclude_replies" to excludeReplies,
            "include_entities" to includeEntities,
            "tweet_mode" to tweetMode,
            "include_rts" to includeRTs,
            "include_my_retweet" to includeMyRetweet,
            *options
        )
    }.jsonArray<Status>()

    fun mention(
        count: Int? = null,
        sinceId: Long? = null,
        maxId: Long? = null,
        trimUser: Boolean? = null,
        includeEntities: Boolean? = null,
        tweetMode: String? = null,
        includeRTs: Boolean? = null,
        includeMyRetweet: Boolean? = null,
        vararg options: Option
    ) = client.session.get("/1.1/statuses/mentions_timeline.json") {
        parameter(
            "cards_platform" to "iPhone-13",
            "contributor_details" to "1",
            "count" to "20",
            "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
            "filters" to "",
            "forceBuckets" to "",
            "include_cards" to "1",
            "include_carousels" to "1",
            "include_entities" to "1",
            "include_ext_media_color" to "true",
            "include_media_features" to "true",
            "include_my_retweet" to "1",
            "include_profile_interstitial_type" to "true",
            "include_profile_location" to "true",
            "include_reply_count" to "1",
            "include_user_entities" to "true",
            "include_user_hashtag_entities" to "true",
            "include_user_mention_entities" to "true",
            "include_user_symbol_entities" to "true",
            "tweet_mode" to "extended",
            emulationMode = EmulationMode.TwitterForiPhone
        )
        parameter(
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "trim_user" to trimUser,
            "include_entities" to includeEntities,
            "tweet_mode" to tweetMode,
            "include_my_retweet" to includeMyRetweet,
            "include_rts" to includeRTs,
            *options
        )
    }.jsonArray<Status>()

    fun user(
        count: Int? = null,
        sinceId: Long? = null,
        maxId: Long? = null,
        trimUser: Boolean? = null,
        excludeReplies: Boolean? = null,
        includeRTs: Boolean? = null,
        tweetMode: String? = null,
        includeEntities: Boolean? = null,
        includeMyRetweet: Boolean? = null,
        vararg options: Option
    ) = client.session.get("/1.1/statuses/user_timeline.json") {
        parameter(
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "trim_user" to trimUser,
            "exclude_replies" to excludeReplies,
            "include_rts" to includeRTs,
            "tweet_mode" to tweetMode,
            "include_entities" to includeEntities,
            "include_my_retweet" to includeMyRetweet,
            *options
        )
    }.jsonArray<Status>()

    fun user(
        userId: Long,
        count: Int? = null,
        sinceId: Long? = null,
        maxId: Long? = null,
        trimUser: Boolean? = null,
        excludeReplies: Boolean? = null,
        includeRTs: Boolean? = null,
        tweetMode: String? = null,
        includeEntities: Boolean? = null,
        includeMyRetweet: Boolean? = null,
        vararg options: Option
    ) = client.session.get("/1.1/statuses/user_timeline.json") {
        parameter(
            "user_id" to userId,
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "trim_user" to trimUser,
            "exclude_replies" to excludeReplies,
            "include_rts" to includeRTs,
            "tweet_mode" to tweetMode,
            "include_entities" to includeEntities,
            "include_my_retweet" to includeMyRetweet,
            *options
        )
    }.jsonArray<Status>()

    fun user(
            screenName: String,
            count: Int? = null,
            sinceId: Long? = null,
            maxId: Long? = null,
            trimUser: Boolean? = null,
            excludeReplies: Boolean? = null,
            includeRTs: Boolean? = null,
            tweetMode: String? = null,
            includeEntities: Boolean? = null,
            includeMyRetweet: Boolean? = null,
            vararg options: Option
    ) = client.session.get("/1.1/statuses/user_timeline.json") {
        parameter(
                "screen_name" to screenName,
                "count" to count,
                "since_id" to sinceId,
                "max_id" to maxId,
                "trim_user" to trimUser,
                "exclude_replies" to excludeReplies,
                "include_rts" to includeRTs,
                "tweet_mode" to tweetMode,
                "include_entities" to includeEntities,
                "include_my_retweet" to includeMyRetweet,
                *options
        )
    }.jsonArray<Status>()
}

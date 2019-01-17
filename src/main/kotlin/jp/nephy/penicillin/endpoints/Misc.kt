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

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.BadgeCount
import jp.nephy.penicillin.models.DMUserUpdate

class Misc(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun badgeCount(vararg options: Pair<String, Any?>) = client.session.get("/2/badge_count/badge_count.json") {
        parameter(
            "cards_platform" to "iPhone-13",
            "contributor_details" to 1,
            "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
            "include_cards" to 1,
            "include_carousels" to 1,
            "include_entities" to 1,
            "include_ext_media_color" to true,
            "include_media_features" to true,
            "include_my_retweet" to 1,
            "include_profile_interstitial_type" to true,
            "include_profile_location" to true,
            "include_reply_count" to 1,
            "include_user_entities" to true,
            "include_user_hashtag_entities" to true,
            "include_user_mention_entities" to true,
            "include_user_symbol_entities" to true,
            "model_version" to 9,
            "tweet_mode" to "extended",
            "use_truncated_counts" to false,
            *options
        )
    }.jsonObject<BadgeCount>()

    @PrivateEndpoint
    fun userUpdates(cursor: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/dm/user_updates.json") {
        parameter(
            "cards_platform" to "iPhone-13",
            "contributor_details" to "1",
            "cursor" to cursor,
            "dm_users" to "true",
            "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
            "include_cards" to "1",
            "include_carousels" to "1",
            "include_entities" to "1",
            "include_ext_media_color" to "true",
            "include_groups" to "true",
            "include_inbox_timelines" to "true",
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
            *options
        )
    }.jsonObject<DMUserUpdate>()

    @PrivateEndpoint
    fun promptSuggest(vararg options: Pair<String, Any?>) = client.session.get("/1.1/prompts/suggest.json") {
        parameter(
            "client_namespace" to "native",
            "consecutive_days" to "1",
            "force_fatigue_on_override" to "false",
            "format" to "home_timeline",
            "has_unknown_phone_number" to "false",
            "lang" to "ja",
            "notifications_device" to "true",
            *options
        )
    }.empty()
}

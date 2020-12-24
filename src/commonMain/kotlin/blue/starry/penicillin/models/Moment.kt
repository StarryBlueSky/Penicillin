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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.jsonkt.parseObject
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.jsonObject

public data class Moment(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    private val moment by jsonObject
    public val id: String by moment.byString
    public val title: String by moment.byString
    public val description: String by moment.byString
    public val url: String by moment.byString
    public val isLive: Boolean by moment.byBoolean("is_live")
    public val time: String by moment.byString("time_string")
    public val lastPublishTimeRaw: String by moment.byString("last_publish_time")
    public val subcategory: String by moment.byString("subcategory_string")
    public val sensitive: Boolean by moment.byBoolean
    public val duration: String by moment.byString("duration_string")
    public val canSubscribe: Boolean by moment.byBoolean("can_subscribe")
    public val capsuleContentsVersion: String by moment.byString("capsule_contents_version")
    public val totalLikes: Int by moment.byInt("total_likes")
    public val users: List<User> by moment.byLambda {
        it.jsonObject.toMap().values.map { json ->
            json.parseObject { obj ->
                User(obj, client)
            }
        }
    }
    public val coverMedia: CoverMedia by moment.byModel("cover_media") { CoverMedia(it, client) }
    public val displayStyle: String by string("display_style")
    private val context by jsonObject
    private val contextScribeInfo by context.byJsonObject
    public val momentPosition: String by contextScribeInfo.byString("moment_position")
    public val tweets: List<Status> by lambda {
        it.jsonObject.toMap().values.map { json ->
            json.parseObject { obj ->
                Status(obj, client)
            }
        }
    }
    public val coverFormat: CoverFormat by model("cover_format") { CoverFormat(it, client) }
    public val largeFormat: CoverFormat by model("large_format") { CoverFormat(it, client) }
    public val thumbnailFormat: CoverFormat by model("thumbnail_format") { CoverFormat(it, client) }

    public data class CoverFormat(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonCoverMedia(parentJson, parentClient) {
        public val pageId: String by string("page_id")
        public val isPromoted: Boolean by boolean("is_promoted")
        private val linkTitleCard by jsonObject("link_title_card")
        public val linkUrl: String by linkTitleCard.byString("url")
        public val linkDisplayUrl: String by linkTitleCard.byString("display_url")
        public val linkVanitySource: String by linkTitleCard.byString("vanity_source")
        public val linkTitle: String by linkTitleCard.byString("title")
    }
}

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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.byPenicillinModel
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.extensions.penicillinModel

data class Moment(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    private val moment by jsonObject
    val id by moment.byString
    val title by moment.byString
    val description by moment.byString
    val url by moment.byString
    val isLive by moment.byBoolean("is_live")
    val time by moment.byString("time_string")
    // val lastPublishTime by moment.byString("last_publish_time")
    val subcategory by moment.byString("subcategory_string")
    val sensitive by moment.byBoolean
    val duration by moment.byString("duration_string")
    val canSubscribe by moment.byBoolean("can_subscribe")
    val capsuleContentsVersion by moment.byString("capsule_contents_version")
    val totalLikes by moment.byInt("total_likes")
    val users by moment.byLambda { it.jsonObject.toMap().values.map { json -> json.parseModel<User>(client) } }
    val coverMedia by moment.byPenicillinModel<CoverMedia>(client, "cover_media")
    val displayStyle by string("display_style")
    private val context by jsonObject
    private val contextScribeInfo by context.byJsonObject
    val momentPosition by contextScribeInfo.byString("moment_position")
    val tweets by lambda { it.jsonObject.toMap().values.map { json -> json.parseModel<Status>(client) } }
    val coverFormat by penicillinModel<CoverFormat>("cover_format")
    val largeFormat by penicillinModel<CoverFormat>("large_format")
    val thumbnailFormat by penicillinModel<CoverFormat>("thumbnail_format")

    data class CoverFormat(val parentJson: JsonObject, override val client: ApiClient): CommonCoverMedia(parentJson, client) {
        val pageId by string("page_id")
        val isPromoted by boolean("is_promoted")
        private val linkTitleCard by jsonObject("link_title_card")
        val linkUrl by linkTitleCard.byString("url")
        val linkDisplayUrl by linkTitleCard.byString("display_url")
        val linkVanitySource by linkTitleCard.byString("vanity_source")
        val linkTitle by linkTitleCard.byString("title")
    }
}

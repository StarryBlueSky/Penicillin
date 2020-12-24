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
import blue.starry.penicillin.core.session.ApiClient


public data class SearchTypeahead(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val completedIn: Float by float("completed_in")
    public val hashtags: List<String> by stringList
    public val numResults: Int by int("num_results")
    public val oneclick: List<String> by stringList
    public val query: String by string
    public val topics: List<Topic> by modelList { Topic(it, client) }
    public val users: List<UserTypeahead> by modelList { UserTypeahead(it, client) }

    public data class Topic(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val inline: Boolean by boolean
        public val roundedScore: Int by int("rounded_score")
        public val tokens: List<SearchToken> by modelList { SearchToken(it, client) }
        public val topic: String by string
    }

    public data class UserTypeahead(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val canMediaTag: Boolean by boolean("can_media_tag")
        public val connectingUserCount: Int by int("connecting_user_count")
        public val connectingUserIds: List<Long> by longList("connecting_user_ids")
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val inline: Boolean by boolean
        public val isBlocked: Boolean by boolean("is_blocked")
        public val isDmAble: Boolean by boolean("is_dm_able")
        public val isProtected: Boolean by boolean("is_protected")
        public val location: String? by nullableString
        public val name: String by string
        public val profileImageUrl: String? by nullableString("profile_image_url")
        public val profileImageUrlHttps: String? by nullableString("profile_image_url_https")
        public val roundedGraphWeight: Int by int("rounded_graph_weight")
        public val roundedScore: Int by int("rounded_score")
        public val screenName: String by string("screen_name")
        public val socialContext: SocialContext by model("social_context") { SocialContext(it, client) }
        public val socialProof: Int by int("social_proof")
        public val socialProofsOrdered: List<Int> by intList("social_proofs_ordered")
        public val tokens: List<SearchToken> by modelList { SearchToken(it, client) }
        public val verified: Boolean by boolean

        public data class SocialContext(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val following: Boolean by boolean
            public val followedBy: Boolean by boolean("followed_by")
        }
    }

    public data class SearchToken(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val token: String by string
    }
}

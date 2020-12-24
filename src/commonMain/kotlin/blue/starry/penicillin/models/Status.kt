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

import blue.starry.jsonkt.JsonElement
import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient



import blue.starry.penicillin.models.entities.StatusEntity

public data class Status(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val createdAtRaw: String by string("created_at")
    public val id: Long by long
    public val idStr: String by string("id_str")
    
    public val textRaw: String? by nullableString("text")
    public val fullTextRaw: String? by nullableString("full_text")
    public val displayTextRange: List<Int> by intList("display_text_range")
    
    public val entities: StatusEntity by model { StatusEntity(it, client) }
    public val extendedEntities: StatusEntity? by nullableModel("extended_entities") { StatusEntity(it, client) }
    
    public val truncated: Boolean by boolean
    public val source: String by string
    
    public val inReplyToScreenName: String? by nullableString("in_reply_to_screen_name")
    public val inReplyToStatusId: Long? by nullableLong("in_reply_to_status_id")
    public val inReplyToStatusIdStr: String? by nullableString("in_reply_to_status_id_str")
    public val inReplyToUserId: Long? by nullableLong("in_reply_to_user_id")
    public val inReplyToUserIdStr: String? by nullableString("in_reply_to_user_id_str")
    
    public val user: User by model { User(it, client) }
    
    @Deprecated("geo field is deprecated. Use coordinates instead.", replaceWith = ReplaceWith("coordinates"))
    public val geo: JsonElement? by nullableJsonElement
    public val coordinates: Coordinate? by nullableModel { Coordinate(it, client) }
    public val place: Place? by nullableModel { Place(it, client) }
    
    public val contributors: List<Contributor> by modelList { Contributor(it, client) }
    
    public val quotedStatusId: Long? by nullableLong("quoted_status_id")
    public val quotedStatusIdStr: String? by nullableString("quoted_status_id_str")
    public val quotedStatus: Status? by nullableModel("quoted_status") { Status(it, client) }
    public val isQuoteStatus: Boolean by boolean("is_quote_status")
    
    public val retweetCount: Int by int("retweet_count")
    public val favoriteCount: Int by int("favorite_count")
    // With includeQuoteCount=true; Maybe enterprise only
    public val quoteCount: Int? by nullableInt("quote_count")
    // With includeReplyCount=true; Maybe enterprise only
    public val replyCount: Int? by nullableInt("reply_count")
    
    public val favorited: Boolean by boolean
    public val retweeted: Boolean by boolean
    
    public val possiblySensitive: Boolean by boolean("possibly_sensitive")
    public val possiblySensitiveAppealable: Boolean? by nullableBoolean("possibly_sensitive_appealable")
    public val possiblySensitiveEditable: Boolean? by nullableBoolean("possibly_sensitive_editable")
    
    public val langRaw: String by string("lang")
    
    // With includeCardUri=true
    public val cardUri: String? by nullableString("card_uri")
    
    // Unknown
    public val conversationId: Long? by nullableLong("conversation_id")
    public val currentUserRetweet: CurrentUserRetweet? by nullableModel("current_user_retweet") { CurrentUserRetweet(it, client) }
    public val extendedTweet: ExtendedTweet? by nullableModel("extended_tweet") { ExtendedTweet(it, client) }
    public val filterLevel: String? by nullableString("filter_level")
    public val retweetedStatus: Status? by nullableModel("retweeted_status") { Status(it, client) }
    public val supplementalLanguage: String? by nullableString("supplemental_language") // null
    public val timestampMs: String? by nullableString("timestamp_ms")
    public val withheldCopyright: Boolean? by nullableBoolean("withheld_copyright")
    public val withheldInCountries: List<String> by stringList("withheld_in_countries")
    public val withheldScope: String? by nullableString("withheld_scope")
    public val matchingRules: List<MatchingRule?> by nullableModelList("matching_rules") { MatchingRule(it, client) }

    public data class Contributor(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val screenName: String by string("screen_name")
    }

    public data class Coordinate(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val coordinates by floatList
        public val type: String by string
        public val longitude: Float? by nullableLambda {
            if (coordinates.size == 2) {
                coordinates[0]
            } else {
                null
            }
        }
        public val latitude: Float? by nullableLambda {
            if (coordinates.size == 2) {
                coordinates[1]
            } else {
                null
            }
        }
    }

    public data class CurrentUserRetweet(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val id: Long by long
        public val idStr: String by string("id_str")
    }

    public data class ExtendedTweet(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val displayTextRange: List<Int> by intList("display_text_range")
        public val entities: StatusEntity by model { StatusEntity(it, client) }
        public val extendedEntities: StatusEntity? by nullableModel("extended_entities") { StatusEntity(it, client) }
        public val fullText: String? by nullableString("full_text")
    }

    public data class MatchingRule(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val tag: String by string
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as? Status)?.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

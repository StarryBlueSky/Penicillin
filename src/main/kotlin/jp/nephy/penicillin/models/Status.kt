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

import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.nullablePenicillinModel
import jp.nephy.penicillin.extensions.penicillinModel
import jp.nephy.penicillin.extensions.penicillinModelList
import jp.nephy.penicillin.models.entities.StatusEntity

data class Status(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val createdAtRaw by string("created_at")
    val id by long
    val idStr by string("id_str")
    
    val textRaw by nullableString("text")
    val fullTextRaw by nullableString("full_text")
    val displayTextRange by intList("display_text_range")
    
    val entities by penicillinModel<StatusEntity>()
    val extendedEntities by nullablePenicillinModel<StatusEntity>("extended_entities")
    
    val truncated by boolean
    val source by string
    
    val inReplyToScreenName by nullableString("in_reply_to_screen_name")
    val inReplyToStatusId by nullableLong("in_reply_to_status_id")
    val inReplyToStatusIdStr by nullableString("in_reply_to_status_id_str")
    val inReplyToUserId by nullableLong("in_reply_to_user_id")
    val inReplyToUserIdStr by nullableString("in_reply_to_user_id_str")
    
    val user by penicillinModel<User>()
    
    @Deprecated("geo field is deprecated. Use coordinates instead.", replaceWith = ReplaceWith("coordinates"))
    val geo by nullableJsonElement
    val coordinates by nullablePenicillinModel<Coordinate>()
    val place by nullablePenicillinModel<Place>()
    
    val contributors by penicillinModelList<Contributor>()
    
    val quotedStatusId by nullableLong("quoted_status_id")
    val quotedStatusIdStr by nullableString("quoted_status_id_str")
    val quotedStatus by nullablePenicillinModel<Status>("quoted_status")
    val isQuoteStatus by boolean("is_quote_status")
    
    val retweetCount by int("retweet_count")
    val favoriteCount by int("favorite_count")
    // With includeQuoteCount=true; Maybe enterprise only
    val quoteCount by nullableInt("quote_count")
    // With includeReplyCount=true; Maybe enterprise only
    val replyCount by nullableInt("reply_count")
    
    val favorited by boolean
    val retweeted by boolean
    
    val possiblySensitive by boolean("possibly_sensitive")
    val possiblySensitiveAppealable by nullableBoolean("possibly_sensitive_appealable")
    val possiblySensitiveEditable by nullableBoolean("possibly_sensitive_editable")
    
    val langRaw by string("lang")
    
    // With includeCardUri=true
    val cardUri by nullableString("card_uri")
    
    // Unknown
    val conversationId by nullableLong("conversation_id")
    val currentUserRetweet by nullablePenicillinModel<CurrentUserRetweet>("current_user_retweet")
    val extendedTweet by nullablePenicillinModel<ExtendedTweet>("extended_tweet")
    val filterLevel by nullableString("filter_level")
    val retweetedStatus by nullablePenicillinModel<Status>("retweeted_status")
    val supplementalLanguage by nullableString("supplemental_language") // null
    val timestampMs by nullableString("timestamp_ms")
    val withheldCopyright by nullableBoolean("withheld_copyright")
    val withheldInCountries by stringList("withheld_in_countries")
    val withheldScope by nullableString("withheld_scope")

    data class Contributor(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val id by long
        val idStr by string("id_str")
        val screenName by string("screen_name")
    }

    data class Coordinate(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val coordinates by floatList
        val type by string
        val longitude by nullableLambda {
            if (coordinates.size == 2) {
                coordinates[0]
            } else {
                null
            }
        }
        val latitude by nullableLambda {
            if (coordinates.size == 2) {
                coordinates[1]
            } else {
                null
            }
        }
    }

    data class CurrentUserRetweet(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val id by long
        val idStr by string("id_str")
    }

    data class ExtendedTweet(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val displayTextRange by intList("display_text_range")
        val entities by penicillinModel<StatusEntity>()
        val extendedEntities by nullablePenicillinModel<StatusEntity>("extended_entities")
        val fullText by nullableString("full_text")
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as? Status)?.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

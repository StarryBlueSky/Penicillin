@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt
import jp.nephy.penicillin.models.special.Language
import jp.nephy.penicillin.models.special.Source
import jp.nephy.penicillin.models.special.StatusID
import kotlinx.serialization.json.long

data class Status(override val json: JsonObject): PenicillinModel {
    val contributors by modelList<Contributor>()
    val conversationId by nullableLong("conversation_id")
    val coordinates by model<Coordinate?>()
    val currentUserRetweet by model<UserRetweet?>(key = "current_user_retweet")
    val createdAt by lambda("created_at") { CreatedAt(it.string) }
    val displayTextRange by intList("display_text_range")
    val entities by model<StatusEntity>()
    val extendedEntities by model<ExtendedEntity?>(key = "extended_entities")
    val extendedTweet by model<ExtendedTweet?>(key = "extended_tweet")
    val favoriteCount by int("favorite_count")
    val favorited by boolean
    val filterLevel by nullableString("filter_level")
    val fullText by nullableString("full_text")
    @Deprecated("geo field is deprecated. Use coordinates instead.")
    val geo by nullableJsonObject
    val id by long
    val idObj by lambda("id") { StatusID(it.long) }
    val idStr by string("id_str")
    val inReplyToScreenName by nullableString("in_reply_to_screen_name")
    val inReplyToStatusId by nullableLong("in_reply_to_status_id")
    val inReplyToStatusIdObj by nullableLambda("in_reply_to_status_id") { StatusID(it.long) }
    val inReplyToStatusIdStr by nullableString("in_reply_to_status_id_str")
    val inReplyToUserId by nullableLong("in_reply_to_user_id")
    val inReplyToUserIdStr by nullableString("in_reply_to_user_id_str")
    val isQuoteStatus by boolean("is_quote_status")
    val lang by lambda { Language(it.string) }
    val place by model<Place?>()
    val possiblySensitive by nullableBoolean("possibly_sensitive")
    val possiblySensitiveEditable by nullableBoolean("possibly_sensitive_editable")
    val quotedStatus by model<Status?>(key = "quoted_status")
    val quotedStatusId by nullableLong("quoted_status_id")
    val quotedStatusIdObj by nullableLambda("quoted_status_id") { StatusID(it.long) }
    val quotedStatusIdStr by nullableString("quoted_status_id_str")
    val quoteCount by nullableInt("quote_count")
    val replyCount by nullableInt("reply_count")
    val retweetCount by int("retweet_count")
    val retweeted by boolean
    val retweetedStatus by model<Status?>(key = "retweeted_status")
    val source by lambda { Source(it.string) }
    val supplementalLanguage by nullableString("supplemental_language") // null
    val text by string
    val timestampMs by nullableString("timestamp_ms")
    val truncated by boolean
    val user by model<User>()
    val withheldCopyright by nullableBoolean("withheld_copyright")
    val withheldInCountries by stringList("withheld_in_countries")
    val withheldScope by nullableString("withheld_scope")

    fun fullText(): String {
        return if (retweetedStatus != null) {
            if (retweetedStatus?.extendedTweet != null) {
                "RT @${retweetedStatus!!.user.screenName}: ${retweetedStatus!!.extendedTweet!!.fullText}"
            } else {
                fullText ?: text
            }
        } else {
            extendedTweet?.fullText ?: fullText ?: text
        }
    }
}

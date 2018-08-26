package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.models.special.CreatedAt
import jp.nephy.penicillin.models.special.Language
import jp.nephy.penicillin.models.special.Source
import jp.nephy.penicillin.models.special.StatusID


class Status(override val json: JsonObject): PenicillinModel {
    val contributors by json.byModelList<Contributor>()
    val conversationId by json.byNullableLong("conversation_id")
    val coordinates by json.byModel<Coordinate?>()
    val currentUserRetweet by json.byModel<UserRetweet?>(key = "current_user_retweet")
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val displayTextRange by json.byIntList("display_text_range")
    val entities by json.byModel<StatusEntity>()
    val extendedEntities by json.byModel<ExtendedEntity?>(key = "extended_entities")
    val extendedTweet by json.byModel<ExtendedTweet?>(key = "extended_tweet")
    val favoriteCount by json.byInt("favorite_count")
    val favorited by json.byBool
    val filterLevel by json.byNullableString("filter_level")
    private val fullTextInternal by json.byNullableString("full_text")
    @Deprecated("geo field is deprecated. Use coordinates instead.")
    val geo by json.byNullableJsonObject
    val id by json.byLong
    val idObj by json.byLambda("id") { StatusID(id) }
    val idStr by json.byString("id_str")
    val inReplyToScreenName by json.byNullableString("in_reply_to_screen_name")
    val inReplyToStatusId by json.byNullableLong("in_reply_to_status_id")
    val inReplyToStatusIdObj by json.byNullableLambda("in_reply_to_status_id") { StatusID(long) }
    val inReplyToStatusIdStr by json.byNullableString("in_reply_to_status_id_str")
    val inReplyToUserId by json.byNullableLong("in_reply_to_user_id")
    val inReplyToUserIdStr by json.byNullableString("in_reply_to_user_id_str")
    val isQuoteStatus by json.byBool("is_quote_status")
    val lang by json.byLambda { Language(string) }
    val place by json.byModel<Place?>()
    val possiblySensitive by json.byNullableBool("possibly_sensitive")
    val possiblySensitiveEditable by json.byNullableBool("possibly_sensitive_editable")
    val quotedStatus by json.byModel<Status?>(key = "quoted_status")
    val quotedStatusId by json.byNullableLong("quoted_status_id")
    val quotedStatusIdObj by json.byNullableLambda("quoted_status_id") { StatusID(long) }
    val quotedStatusIdStr by json.byNullableString("quoted_status_id_str")
    val quoteCount by json.byNullableInt("quote_count")
    val replyCount by json.byNullableInt("reply_count")
    val retweetCount by json.byInt("retweet_count")
    val retweeted by json.byBool
    val retweetedStatus by json.byModel<Status?>(key = "retweeted_status")
    val source by json.byLambda { Source(string) }
    val supplementalLanguage by json.byNullableString("supplemental_language") // null
    val text by json.byString
    val timestampMs by json.byNullableString("timestamp_ms")
    val truncated by json.byBool
    val user by json.byModel<User>()
    val withheldCopyright by json.byNullableBool("withheld_copyright")
    val withheldInCountries by json.byStringList("withheld_in_countries")
    val withheldScope by json.byNullableString("withheld_scope")

    val fullText: String
        get() = if (retweetedStatus != null) {
            if (retweetedStatus?.extendedTweet != null) {
                "RT @${retweetedStatus !!.user.screenName}: ${retweetedStatus !!.extendedTweet !!.fullText}"
            } else {
                text
            }
        } else {
            if (extendedTweet != null) {
                extendedTweet?.fullText ?: text
            } else {
                text
            }
        }
}

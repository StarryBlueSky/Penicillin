package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.Country
import jp.nephy.penicillin.misc.CreatedAt
import jp.nephy.penicillin.misc.Language
import jp.nephy.penicillin.misc.Source
import jp.nephy.penicillin.misc.StatusID

@Suppress("UNUSED")
class Status(val json: JsonElement) {
    val contributors by json.byList<Contributor>()
    val conversationId by json.byNullableLong("conversation_id")
    val coordinates by json.byModel<Coordinate?>()
    val currentUserRetweet by json.byModel<UserRetweet?>("current_user_retweet")
    val createdAt by json.byConverter<String, CreatedAt>("created_at")
    val displayTextRange by json.byList<Int>("display_text_range")
    val entities by json.byModel<StatusEntity>()
    val extendedEntities by json.byModel<ExtendedEntity?>("extended_entities")
    val extendedTweet by json.byModel<ExtendedTweet?>("extended_tweet")
    val favoriteCount by json.byInt("favorite_count")
    val favorited by json.byBool
    val filterLevel by json.byNullableString("filter_level")
    val fullText by json.byNullableString("full_text")
    @Deprecated("geo field is deprecated. Use coordinates instead.")
    val geo by json.byNullableObject
    val id by json.byConverter<Long, StatusID>()
    val idStr by json.byString("id_str")
    val inReplyToScreenName by json.byNullableString("in_reply_to_screen_name")
    val inReplyToStatusId by json.byConverter<Long, StatusID>("in_reply_to_status_id")
    val inReplyToStatusIdStr by json.byNullableString("in_reply_to_status_id_str")
    val inReplyToUserId by json.byNullableLong("in_reply_to_user_id")
    val inReplyToUserIdStr by json.byNullableString("in_reply_to_user_id_str")
    val isQuoteStatus by json.byBool("is_quote_status")
    val lang by json.byModel<Language>()
    val place by json.byModel<Place?>()
    val possiblySensitive by json.byNullableBool("possibly_sensitive")
    val possiblySensitiveEditable by json.byNullableBool("possibly_sensitive_editable")
    val quotedStatus by json.byModel<Status?>("quoted_status")
    val quotedStatusId by json.byConverter<Long, StatusID?>("quoted_status_id")
    val quotedStatusIdStr by json.byNullableString("quoted_status_id_str")
    val quoteCount by json.byNullableInt("quote_count") // 0
    val replyCount by json.byNullableInt("reply_count") // 0
    val retweetCount by json.byInt("retweet_count")
    val retweeted by json.byBool
    val retweetedStatus by json.byModel<Status?>("retweeted_status")
    val source by json.byConverter<String, Source>()
    val supplementalLanguage by json.byNullableString("supplemental_language") // null
    val text by json.byString
    val timestampMs by json.byNullableString("timestamp_ms")
    val truncated by json.byBool
    val user by json.byModel<User>()
    val withheldCopyright by json.byNullableBool("withheld_copyright")
    val withheldInCountries by json.byList<Country>("withheld_in_countries", {it.asString})
    val withheldScope by json.byNullableString("withheld_scope")

    fun fullText(): String {
        return if (retweetedStatus != null && retweetedStatus!!.truncated) {
            "RT @${retweetedStatus!!.user.screenName}: ${retweetedStatus!!.extendedTweet!!.fullText}"
        } else if (fullText != null) {
            fullText!!
        } else if (truncated) {
            extendedTweet!!.fullText!!
        } else {
            text
        }
    }
}

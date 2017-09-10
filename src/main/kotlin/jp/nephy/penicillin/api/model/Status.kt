package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.*

class Status(val json: JsonElement) {
    val contributors by json.byNullableContributorArray
    val conversationId by json.byNullableLong("conversation_id")
    val coordinates by json.byNullableCoordinate
    val currentUserRetweet by json.byNullableUserRetweet("current_user_retweet")
    val createdAt by json.byCreatedAt("created_at")
    val entities by json.byStatusEntity
    val favoriteCount by json.byInt("favorite_count")
    val favorited by json.byBool
    val filterLevel by json.byNullableString("filter_level")
    @Deprecated("geo field is deprecated. Use coordinates instead.")
    val geo by json.byNullableObject
    val id by json.byStatusID
    val idStr by json.byString("id_str")
    val inReplyToScreenName by json.byNullableString("in_reply_to_screen_name")
    val inReplyToStatusId by json.byNullableStatusID("in_reply_to_status_id")
    val inReplyToStatusIdStr by json.byNullableString("in_reply_to_status_id_str")
    val inReplyToUserId by json.byNullableLong("in_reply_to_user_id")
    val inReplyToUserIdStr by json.byNullableString("in_reply_to_user_id_str")
    val isQuoteStatus by json.byBool("is_quote_status")
    val lang by json.byLanguage
    val place by json.byNullablePlace
    val possiblySensitive by json.byNullableBool("possibly_sensitive")
    val possiblySensitiveEditable by json.byNullableBool("possibly_sensitive_editable")
    val quotedStatus by json.byNullableStatus("quoted_status")
    val quotedStatusId by json.byNullableStatusID("quoted_status_id")
    val quotedStatusIdStr by json.byNullableString("quoted_status_id_str")
    val retweetCount by json.byInt("retweet_count")
    val retweeted by json.byBool
    val retweetedStatus by json.byNullableStatus("retweeted_status")
    val source by json.bySource
    val supplementalLanguage by json.byNullableString("supplemental_language") // null
    val text by json.byString
    val truncated by json.byString
    val user by json.byUser
    val withheldCopyright by json.byNullableBool("withheld_copyright")
    val withheldInCountries by json.byNullableCountryArray("withheld_in_countries")
    val withheldScope by json.byNullableString("withheld_scope")
}

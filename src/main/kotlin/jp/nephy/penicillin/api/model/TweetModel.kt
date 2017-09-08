package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byNullableStatusID
import jp.nephy.penicillin.api.bySource
import jp.nephy.penicillin.api.byStatusID
import jp.nephy.penicillin.api.byUserModel

class TweetModel(val json: JsonElement) {
    val contributors by json.byNullableString // null
    val conversationId by json.byNullableLong("conversation_id") // 903186037354016769
    val coordinates by json.byNullableString // null
    val createdAt by json.byNullableString("created_at") // "Thu Aug 31 10:21:09 +0000 2017"
    val entities by json.byNullableObject // {"hashtags": [], "symbols": [], "user_mentions": [{"screen_name": "staff_aoi", "name": "悠木碧【公式】", "id": 882789714201198592, "id_str": "882789714201198592", "indices": [0, 10]}], "urls": []}
    val favoriteCount by json.byNullableInt("favorite_count") // 5349
    val favorited by json.byNullableBool // true
    val geo by json.byNullableString // null
    val id by json.byStatusID // 903200982280003584
    val idStr by json.byNullableString("id_str") // "903200982280003584"
    val inReplyToScreenName by json.byNullableString("in_reply_to_screen_name") // "staff_aoi"
    val inReplyToStatusId by json.byNullableStatusID("in_reply_to_status_id") // 903186950735052800
    val inReplyToStatusIdStr by json.byNullableString("in_reply_to_status_id_str") // "903186950735052800"
    val inReplyToUserId by json.byNullableLong("in_reply_to_user_id") // 882789714201198592
    val inReplyToUserIdStr by json.byNullableString("in_reply_to_user_id_str") // "882789714201198592"
    val isQuoteStatus by json.byNullableBool("is_quote_status") // false
    val lang by json.byNullableString // "ja"
    val place by json.byNullableString // null
    val retweetCount by json.byNullableInt("retweet_count") // 2854
    val retweeted by json.byNullableBool // false
    val source by json.bySource // "<a href="http://twipple.jp/" rel="nofollow">ついっぷる for Android</a>"
    val supplementalLanguage by json.byNullableString("supplemental_language") // null
    val text by json.byNullableString // "@staff_aoi あおいが洗いたいのはおっぱいでしょ！"
    val truncated by json.byNullableBool // false
    val user by json.byUserModel
}

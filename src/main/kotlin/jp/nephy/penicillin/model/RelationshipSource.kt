package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class RelationshipSource(val json: JsonElement) {
    val allReplies by json.byBool("all_replies")
    val blockedBy by json.byBool("blocked_by")
    val blocking by json.byBool
    val canDm by json.byBool("can_dm")
    val canMediaTag by json.byBool("can_media_tag")
    val followedBy by json.byBool("followed_by")
    val following by json.byBool
    val followingReceived by json.byBool("following_received")
    val followingRequested by json.byBool("following_requested")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val liveFollowing by json.byBool("live_following")
    val markedSpam by json.byBool("marked_spam")
    val muting by json.byBool
    val notificationsEnabled by json.byBool("notifications_enabled")
    val screenName by json.byString("screen_name")
    val wantRetweets by json.byBool("want_retweets")
}

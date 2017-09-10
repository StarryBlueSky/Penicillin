package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class RelationshipTarget(val json: JsonElement) {
    val followedBy by json.byBool("followed_by")
    val following by json.byBool
    val followingReceived by json.byBool("following_received")
    val followingRequested by json.byBool("following_requested")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val screenName by json.byString("screen_name")
}

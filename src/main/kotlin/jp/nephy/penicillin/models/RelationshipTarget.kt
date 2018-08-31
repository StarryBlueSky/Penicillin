package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString


data class RelationshipTarget(override val json: JsonObject): PenicillinModel {
    val followedBy by json.byBool("followed_by")
    val following by json.byBool
    val followingReceived by json.byBool("following_received")
    val followingRequested by json.byBool("following_requested")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val screenName by json.byString("screen_name")
}
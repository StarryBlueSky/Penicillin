package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byCreatedAt
import jp.nephy.penicillin.api.byStatusEntity
import jp.nephy.penicillin.api.byUser

class DirectMessage(val json: JsonElement) {
    val createdAt by json.byCreatedAt("created_at")
    val entities by json.byStatusEntity
    val id by json.byLong
    val idStr by json.byString("id_str")
    val read by json.byBool
    val recipient by json.byUser
    val recipientId by json.byLong("recipient_id")
    val recipientIdStr by json.byString("recipient_id_str")
    val recipientScreenName by json.byString("recipient_screen_name")
    val sender by json.byUser
    val senderId by json.byLong("sender_id")
    val senderIdStr by json.byString("sender_id_str")
    val senderScreenName by json.byString("sender_screen_name")
    val text by json.byString
}

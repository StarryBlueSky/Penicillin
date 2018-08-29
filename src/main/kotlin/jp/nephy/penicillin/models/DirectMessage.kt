package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.models.special.CreatedAt


data class DirectMessage(override val json: JsonObject): PenicillinModel {
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val entities by json.byModel<StatusEntity>()
    val id by json.byLong
    val idStr by json.byString("id_str")
    val read by json.byBool
    val recipient by json.byModel<User>()
    val recipientId by json.byLong("recipient_id")
    val recipientIdStr by json.byString("recipient_id_str")
    val recipientScreenName by json.byString("recipient_screen_name")
    val sender by json.byModel<User>()
    val senderId by json.byLong("sender_id")
    val senderIdStr by json.byString("sender_id_str")
    val senderScreenName by json.byString("sender_screen_name")
    val text by json.byString
}

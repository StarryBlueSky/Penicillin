package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.CreatedAt

class DirectMessage(val json: JsonElement) {
    val createdAt by json.byConverter<String, CreatedAt>("created_at")
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

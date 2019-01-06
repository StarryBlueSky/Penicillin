@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class DirectMessage(override val json: JsonObject): PenicillinModel {
    // val createdAt by string("created_at")
    val entities by model<StatusEntity>()
    val id by long
    val idStr by string("id_str")
    val read by boolean
    val recipient by model<User>()
    val recipientId by long("recipient_id")
    val recipientIdStr by string("recipient_id_str")
    val recipientScreenName by string("recipient_screen_name")
    val sender by model<User>()
    val senderId by long("sender_id")
    val senderIdStr by string("sender_id_str")
    val senderScreenName by string("sender_screen_name")
    val text by string
}

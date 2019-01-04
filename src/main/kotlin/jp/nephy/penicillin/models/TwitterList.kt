@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class TwitterList(override val json: JsonObject): PenicillinModel {
    // val createdAt by string("created_at")
    val description by string
    val following by boolean
    val fullName by string("full_name")
    val id by long
    val idStr by string("id_str")
    val memberCount by int("member_count")
    val mode by string
    val name by string
    val slug by string
    val subscriberCount by int("subscriber_count")
    val uri by string
    val user by model<User>()
}

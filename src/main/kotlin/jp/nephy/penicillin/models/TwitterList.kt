@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt


data class TwitterList(override val json: ImmutableJsonObject): PenicillinModel {
    val createdAt by lambda("created_at") { CreatedAt(it.string) }
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

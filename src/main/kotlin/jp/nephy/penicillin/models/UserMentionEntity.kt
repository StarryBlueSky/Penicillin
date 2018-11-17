@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserMentionEntity(override val json: JsonObject): PenicillinModel {
    val screenName by string("screen_name")
    val name by string
    val id by long
    val idStr by string("id_str")
    val indices by intList()
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string

data class SavedSearch(override val json: JsonObject): PenicillinModel {
    // val createdAt by string("created_at")
    val id by long
    val idStr by string("id_str")
    val name by string
    val position by string
    val query by string
}

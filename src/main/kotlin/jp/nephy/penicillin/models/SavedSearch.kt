@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt


data class SavedSearch(override val json: ImmutableJsonObject): PenicillinModel {
    val createdAt by lambda("created_at") { CreatedAt(it.string) }
    val id by long
    val idStr by string("id_str")
    val name by string
    val position by string
    val query by string
}

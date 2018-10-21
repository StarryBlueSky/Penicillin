@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string

data class Contributor(override val json: ImmutableJsonObject): PenicillinModel {
    val id by long
    val idStr by string("id_str")
    val screenName by string("screen_name")
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string

data class Contributor(override val json: JsonObject): PenicillinModel {
    val id by long
    val idStr by string("id_str")
    val screenName by string("screen_name")
}

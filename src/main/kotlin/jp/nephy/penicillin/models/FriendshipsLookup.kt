package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class FriendshipsLookup(override val json: JsonObject): PenicillinModel {
    val connections by stringList
    val id by long
    val idStr by string("id_str")
    val name by string
    val screenName by string("screen_name")
}

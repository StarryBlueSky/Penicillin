@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserRetweet(override val json: JsonObject): PenicillinModel {
    val id by long
    val idStr by string("id_str")
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Recommendation(override val json: JsonObject): PenicillinModel {
    val token by string
    val userId by string("user_id")
    val user by model<User>()
}

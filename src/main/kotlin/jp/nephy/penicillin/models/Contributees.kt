@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Contributees(override val json: JsonObject): PenicillinModel {
    val admin by boolean
    val user by model<User>()
}

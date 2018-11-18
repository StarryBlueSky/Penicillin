@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class FriendshipsShow(override val json: JsonObject): PenicillinModel {
    val relationship by model<Relationship>()
}

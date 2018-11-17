@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserEntity(override val json: JsonObject): PenicillinModel {
    val url by model<UserProfileEntity>()
    val description by model<UserProfileEntity>()
}

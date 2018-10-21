@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class UserEntity(override val json: ImmutableJsonObject): PenicillinModel {
    val url by model<UserProfileEntity>()
    val description by model<UserProfileEntity>()
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.modelList

data class UserEntity(override val json: JsonObject): PenicillinModel {
    val url by model<UserProfileEntity>()
    val description by model<UserProfileEntity>()

    data class UserProfileEntity(override val json: JsonObject): PenicillinModel {
        val urls by modelList<URLEntity>()
    }
}

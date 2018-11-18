@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserProfileEntity(override val json: JsonObject): PenicillinModel {
    val urls by modelList<URLEntity>()
}

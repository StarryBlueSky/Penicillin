@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserGalleryData(override val json: JsonObject): PenicillinModel {
    val metadata by model<UserMetadata>()
    val data by model<User>()
}

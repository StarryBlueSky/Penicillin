@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class UserGalleryData(override val json: ImmutableJsonObject): PenicillinModel {
    val metadata by model<UserMetadata>()
    val data by model<User>()
}

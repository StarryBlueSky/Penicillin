@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class UserGalleryModule(override val json: ImmutableJsonObject): PenicillinModel {
    val metadata by model<UserGalleryMetadata>()
    val data by modelList<UserGalleryData>()
}

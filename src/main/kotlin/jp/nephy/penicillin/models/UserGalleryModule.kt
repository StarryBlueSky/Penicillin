@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserGalleryModule(override val json: JsonObject): PenicillinModel {
    val metadata by model<UserGalleryMetadata>()
    val data by modelList<UserGalleryData>()
}

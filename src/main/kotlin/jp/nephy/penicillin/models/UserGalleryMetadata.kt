@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class UserGalleryMetadata(override val json: ImmutableJsonObject): PenicillinModel {
    val resultType by string("result_type")
}

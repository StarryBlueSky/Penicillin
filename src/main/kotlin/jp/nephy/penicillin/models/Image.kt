@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Image(override val json: ImmutableJsonObject): PenicillinModel {
    val imageType by string("image_type")
    val w by int
    val h by int
}


@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class PhotoSize(override val json: ImmutableJsonObject): PenicillinModel {
    val h by int
    val resize by string
    val w by int
}

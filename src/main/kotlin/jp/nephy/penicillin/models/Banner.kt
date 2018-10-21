@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Banner(override val json: ImmutableJsonObject): PenicillinModel {
    val h by int
    val w by int
    val url by string
}

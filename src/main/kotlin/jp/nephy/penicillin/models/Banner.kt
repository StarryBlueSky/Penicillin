@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Banner(override val json: JsonObject): PenicillinModel {
    val h by int
    val w by int
    val url by string
}

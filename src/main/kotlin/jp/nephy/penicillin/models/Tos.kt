@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Tos(override val json: JsonObject): PenicillinModel {
    val tos by string
}

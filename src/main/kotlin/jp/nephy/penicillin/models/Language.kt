@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Language(override val json: JsonObject): PenicillinModel {
    val code by string
    val status by string
    val name by string
}

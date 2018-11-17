@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Location(override val json: JsonObject): PenicillinModel {
    val name by string
    val woeid by int
}

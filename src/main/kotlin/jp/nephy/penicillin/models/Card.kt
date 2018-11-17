@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Card(override val json: JsonObject): PenicillinModel {
    val cardUri by string("card_uri")
    val status by string
}

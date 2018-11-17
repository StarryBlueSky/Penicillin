@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byModel
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.jsonObject

data class CardState(override val json: JsonObject): PenicillinModel {
    private val card by jsonObject
    val name by card.byString
    val url by card.byString
    val cardTypeUrl by card.byString("card_type_url")
    val cardPlatform by card.byModel<CardPlatform>()
    val data by card.byModel<CardBindingValue>(key = "binding_values")
}

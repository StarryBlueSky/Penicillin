package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString


data class CardState(override val json: JsonObject): PenicillinModel {
    val name by json["card"].byString
    val url by json["card"].byString
    val cardTypeUrl by json["card"].byString("card_type_url")
    val cardPlatform by json["card"].byModel<CardPlatform>()

    val data by json["card"].byModel<CardBindingValue>(key = "binding_values")
}

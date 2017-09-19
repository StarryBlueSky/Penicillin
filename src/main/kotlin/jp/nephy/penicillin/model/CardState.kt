package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class CardState(val json: JsonElement) {
    val name by json["card"].byString
    val url by json["card"].byString
    val cardTypeUrl by json["card"].byString("card_type_url")
    val cardPlatform by json["card"].byModel<CardPlatform>()

    val data by json["card"].byModel<CardBindingValue>("binding_values")
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

class Card(override val json: JsonObject): JsonModel {
    val cardUri by json.byString("card_uri")
    val status by json.byString
}

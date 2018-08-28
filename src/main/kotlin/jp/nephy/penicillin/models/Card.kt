package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString

class Card(override val json: JsonObject): PenicillinModel {
    val cardUri by json.byString("card_uri")
    val status by json.byString
}

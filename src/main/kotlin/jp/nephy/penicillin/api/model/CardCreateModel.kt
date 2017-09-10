package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class CardCreateModel(val json: JsonElement) {
    val cardUri by json.byString("card_uri")
    val status by json.byString
}

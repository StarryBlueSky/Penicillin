package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString


data class Privacy(override val json: JsonObject): PenicillinModel {
    val privacy by json.byString
}

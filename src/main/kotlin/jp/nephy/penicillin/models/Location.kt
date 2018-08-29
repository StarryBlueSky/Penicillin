package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


data class Location(override val json: JsonObject): PenicillinModel {
    val name by json.byString
    val woeid by json.byInt
}

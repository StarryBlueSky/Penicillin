package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString

data class PlaceType(override val json: JsonObject): PenicillinModel {
    val code by json.byInt
    val name by json.byString
}

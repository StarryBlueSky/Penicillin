package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString

class PlaceType(override val json: JsonObject): JsonModel {
    val code by json.byInt
    val name by json.byString
}

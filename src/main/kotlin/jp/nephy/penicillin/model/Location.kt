package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class Location(override val json: JsonObject): JsonModel {
    val name by json.byString
    val woeid by json.byInt
}

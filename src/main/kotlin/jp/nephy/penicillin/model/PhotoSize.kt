package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class PhotoSize(override val json: JsonObject): JsonModel {
    val h by json.byInt
    val resize by json.byString
    val w by json.byInt
}

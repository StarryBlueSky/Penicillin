package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class Banner(override val json: JsonObject): JsonModel {
    val h by json.byInt
    val w by json.byInt
    val url by json.byString
}

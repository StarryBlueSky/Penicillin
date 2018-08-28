package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class Banner(override val json: JsonObject): PenicillinModel {
    val h by json.byInt
    val w by json.byInt
    val url by json.byString
}

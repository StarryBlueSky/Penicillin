package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


class TrendModule(override val json: JsonObject): PenicillinModel {
    val name by json.byString
    val description by json.byNullableString("meta_description")
    val rank by json.byInt
    val token by json.byString

    val context by json.byModel<TrendContext?>()
    val target by json.byModel<TrendTarget>()
}

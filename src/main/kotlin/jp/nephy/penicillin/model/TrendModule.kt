package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class TrendModule(val json: JsonElement) {
    val name by json.byString
    val description by json.byNullableString("meta_description")
    val rank by json.byInt
    val token by json.byString

    val context by json.byModel<TrendContext?>()
    val target by json.byModel<TrendTarget>()
}
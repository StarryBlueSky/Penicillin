package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class TrendModule(val json: JsonElement) {
    val name by json.byString
    val description by json.byNullableString("meta_description")
    val rank by json.byInt
    val token by json.byString

    val context by json.byModel<TrendContext?>()
    val target by json.byModel<TrendTarget>()
}
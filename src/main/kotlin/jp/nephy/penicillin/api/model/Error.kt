package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class Error(val json: JsonElement) {
    val code by json.byInt
    val name by json.byNullableString
    val message by json.byString
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class PlaceType(val json: JsonElement) {
    val code by json.byInt
    val name by json.byString
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

@Suppress("UNUSED")
class Birthdate(val json: JsonElement) {
    val year by json.byNullableInt
    val month by json.byNullableInt
    val day by json.byNullableInt

    val visibility by json.byString
    val yearVisibility by json.byString("year_visibility")
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Birthdate(override val json: JsonObject): JsonModel {
    val year by json.byNullableInt
    val month by json.byNullableInt
    val day by json.byNullableInt

    val visibility by json.byString
    val yearVisibility by json.byString("year_visibility")
}

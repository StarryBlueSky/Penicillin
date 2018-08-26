package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byString


class Birthdate(override val json: JsonObject): PenicillinModel {
    val year by json.byNullableInt
    val month by json.byNullableInt
    val day by json.byNullableInt

    val visibility by json.byString
    val yearVisibility by json.byString("year_visibility")
}

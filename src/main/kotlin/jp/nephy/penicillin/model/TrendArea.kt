package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

@Suppress("UNUSED")
class TrendArea(override val json: JsonObject): JsonModel {
    val country by json.byString
    val countryCode by json.byNullableString // null
    val name by json.byString
    val parentid by json.byInt
    val placeType by json.byModel<PlaceType>()
    val url by json.byUrl
    val woeid by json.byInt
}

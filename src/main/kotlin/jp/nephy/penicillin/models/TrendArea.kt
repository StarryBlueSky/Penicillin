package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


class TrendArea(override val json: JsonObject): PenicillinModel {
    val country by json.byString
    val countryCode by json.byNullableString // null
    val name by json.byString
    val parentid by json.byInt
    val placeType by json.byModel<PlaceType>()
    val url by json.byString
    val woeid by json.byInt
}

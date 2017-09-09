package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class TrendAreaModel(val json: JsonElement) {
    val country by json.byNullableString // ""
    val countryCode by json.byNullableString // null
    val name by json.byNullableString // "Worldwide"
    val parentid by json.byNullableInt // 0
    val placeType by json.byNullableObject // {"code": 19, "name": "Supername"}
    val url by json.byNullableString // "http://where.yahooapis.com/v1/place/1"
    val woeid by json.byNullableInt // 1
}

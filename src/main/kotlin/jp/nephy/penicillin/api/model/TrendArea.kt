package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byPlaceType
import jp.nephy.penicillin.api.byURL

class TrendArea(val json: JsonElement) {
    val country by json.byString
    val countryCode by json.byNullableString // null
    val name by json.byString
    val parentid by json.byInt
    val placeType by json.byPlaceType
    val url by json.byURL
    val woeid by json.byInt
}

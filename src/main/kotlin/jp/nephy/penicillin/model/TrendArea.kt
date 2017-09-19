package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byModel
import java.net.URL

class TrendArea(val json: JsonElement) {
    val country by json.byString
    val countryCode by json.byNullableString // null
    val name by json.byString
    val parentid by json.byInt
    val placeType by json.byModel<PlaceType>()
    val url by json.byConverter<String, URL>()
    val woeid by json.byInt
}

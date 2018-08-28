package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString


class TrendArea(override val json: JsonObject): PenicillinModel {
    val country by json.byString
    val countryCode by json.byNullableString // null
    val name by json.byString
    val parentid by json.byInt
    val placeType by json.byModel<PlaceType>()
    val url by json.byString
    val woeid by json.byInt
}

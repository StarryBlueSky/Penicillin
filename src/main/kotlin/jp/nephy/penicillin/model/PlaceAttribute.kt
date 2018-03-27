package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byNullableUrl


class PlaceAttribute(override val json: JsonObject): JsonModel {
    val streetAddress by json.byNullableString("street_address")
    val locality by json.byNullableString
    val region by json.byNullableString
    val iso3 by json.byNullableString
    val postalCode by json.byNullableString("postal_code")
    val phone by json.byNullableString
    val twitter by json.byNullableString
    val url by json.byNullableUrl
    val appId by json.byNullableString("app:id")
    val geotagCount by json.byNullableString
}

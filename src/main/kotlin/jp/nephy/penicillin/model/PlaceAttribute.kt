package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byNullableURL

class PlaceAttribute(val json: JsonElement) {
    val streetAddress by json.byNullableString("street_address")
    val locality by json.byNullableString
    val region by json.byNullableString
    val iso3 by json.byNullableString
    val postalCode by json.byNullableString("postal_code")
    val phone by json.byNullableString
    val twitter by json.byNullableString
    val url by json.byNullableURL
    val appId by json.byNullableString("app:id")
    val geotagCount by json.byNullableString
}

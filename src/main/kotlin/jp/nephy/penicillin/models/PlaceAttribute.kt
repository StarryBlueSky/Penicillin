package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableString


data class PlaceAttribute(override val json: JsonObject): PenicillinModel {
    val streetAddress by json.byNullableString("street_address")
    val locality by json.byNullableString
    val region by json.byNullableString
    val iso3 by json.byNullableString
    val postalCode by json.byNullableString("postal_code")
    val phone by json.byNullableString
    val twitter by json.byNullableString
    val url by json.byNullableString
    val appId by json.byNullableString("app:id")
    val geotagCount by json.byNullableString
}

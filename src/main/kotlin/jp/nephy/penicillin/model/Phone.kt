package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableBool
import com.github.salomonbrys.kotson.byNullableLong
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class Phone(val json: JsonElement) {
    val address by json.byNullableString
    val addressForSms by json.byNullableString("address_for_sms")
    val carrier by json.byNullableString
    val countryCode by json.byNullableString("country_code")
    val countryName by json.byNullableString("country_name")
    val createdAt by json.byNullableString("created_at")
    val deviceType by json.byNullableString("device_type")
    val enabledFor by json.byNullableString("enabled_for")
    val id by json.byNullableLong
    val verified by json.byNullableBool
}

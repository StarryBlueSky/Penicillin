package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byNullableBool
import jp.nephy.jsonkt.byNullableLong
import jp.nephy.jsonkt.byNullableString

@Suppress("UNUSED")
class Phone(override val json: JsonObject): JsonModel {
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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.nullableBoolean
import jp.nephy.jsonkt.delegation.nullableLong
import jp.nephy.jsonkt.delegation.nullableString

data class Phone(override val json: JsonObject): PenicillinModel {
    val address by nullableString
    val addressForSms by nullableString("address_for_sms")
    val carrier by nullableString
    val countryCode by nullableString("country_code")
    val countryName by nullableString("country_name")
    val createdAt by nullableString("created_at")
    val deviceType by nullableString("device_type")
    val enabledFor by nullableString("enabled_for")
    val id by nullableLong
    val verified by nullableBoolean
}

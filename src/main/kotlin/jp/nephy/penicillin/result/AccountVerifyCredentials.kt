package jp.nephy.penicillin.result

import com.github.salomonbrys.kotson.byNullableArray
import com.github.salomonbrys.kotson.byNullableBool
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.model.User

class AccountVerifyCredentials(json: JsonElement): User(json) {
    val advertiserAccountServiceLevels by json.byNullableArray("advertiser_account_service_levels") // ["analytics"]
    val advertiserAccountType by json.byNullableString("advertiser_account_type") // "promotable_user"
    val analyticsType by json.byNullableString("analytics_type") // "enabled"
    val email by json.byNullableString // ""
    val needsPhoneVerification by json.byNullableBool("needs_phone_verification") // false
    val phone by json.byNullableObject // {"id": 797659999379943424, "created_at": "Sun Nov 13 04:38:58 +0000 2016", "address": "+81**********", "address_for_sms": "40404", "verified": true, "enabled_for": "#", "carrier": "jp.softbank_mobile", "country_name": "日本", "country_code": "81", "device_type": "phone"}
    val suspended by json.byNullableBool // false
}

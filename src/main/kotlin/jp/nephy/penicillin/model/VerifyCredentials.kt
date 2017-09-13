package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byPhone
import jp.nephy.penicillin.converter.byStringArray

class VerifyCredentials(json: JsonElement): User(json) {
    val advertiserAccountServiceLevels by json.byStringArray("advertiser_account_service_levels")
    val advertiserAccountType by json.byString("advertiser_account_type")
    val analyticsType by json.byString("analytics_type")
    val email by json.byString
    val needsPhoneVerification by json.byBool("needs_phone_verification")
    val phone by json.byPhone
    val suspended by json.byBool
}

package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byResources

class ApplicationRateLimitStatus(val json: JsonElement) {
    val accessToken by json["rate_limit_content"].byNullableString("access_token")
    val application by json["rate_limit_content"].byNullableString
    val resources by json.byResources
}

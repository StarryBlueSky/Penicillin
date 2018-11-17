@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.model

data class ApplicationRateLimitStatus(override val json: JsonObject): PenicillinModel {
    val accessToken by json["rate_limit_content"].jsonObject.byNullableString("access_token")
    val application by json["rate_limit_content"].jsonObject.byNullableString
    val resources by model<Resources>()
}

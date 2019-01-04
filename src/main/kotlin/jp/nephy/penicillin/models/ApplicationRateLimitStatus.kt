@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.jsonObject
import jp.nephy.jsonkt.delegation.model

data class ApplicationRateLimitStatus(override val json: JsonObject): PenicillinModel {
    val content by jsonObject("rate_limit_content")
    val accessToken by content.byNullableString("access_token")
    val application by content.jsonObject.byNullableString
    val resources by model<Resources>()
}

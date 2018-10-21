@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.immutableJsonObject

data class ApplicationRateLimitStatus(override val json: ImmutableJsonObject): PenicillinModel {
    val accessToken by json["rate_limit_content"].immutableJsonObject.byNullableString("access_token")
    val application by json["rate_limit_content"].immutableJsonObject.byNullableString
    val resources by model<Resources>()
}

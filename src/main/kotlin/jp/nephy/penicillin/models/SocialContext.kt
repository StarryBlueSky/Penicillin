package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class SocialContext(override val json: JsonObject): PenicillinModel {
    val following by boolean
    val followedBy by boolean("followed_by")
}

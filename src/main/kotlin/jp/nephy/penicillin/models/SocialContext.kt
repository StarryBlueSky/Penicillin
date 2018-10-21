@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class SocialContext(override val json: ImmutableJsonObject): PenicillinModel {
    val following by boolean
    val followedBy by boolean("followed_by")
}

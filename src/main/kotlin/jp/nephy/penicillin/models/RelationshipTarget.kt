@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class RelationshipTarget(override val json: ImmutableJsonObject): PenicillinModel {
    val followedBy by boolean("followed_by")
    val following by boolean
    val followingReceived by boolean("following_received")
    val followingRequested by boolean("following_requested")
    val id by long
    val idStr by string("id_str")
    val screenName by string("screen_name")
}

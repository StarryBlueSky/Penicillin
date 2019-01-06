@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class Relationship(override val json: JsonObject): PenicillinModel {
    val source by model<Source>()
    val target by model<Target>()

    data class Source(override val json: JsonObject): PenicillinModel {
        val allReplies by boolean("all_replies")
        val blockedBy by boolean("blocked_by")
        val blocking by boolean
        val canDm by boolean("can_dm")
        val canMediaTag by boolean("can_media_tag")
        val followedBy by boolean("followed_by")
        val following by boolean
        val followingReceived by boolean("following_received")
        val followingRequested by boolean("following_requested")
        val id by long
        val idStr by string("id_str")
        val liveFollowing by boolean("live_following")
        val markedSpam by boolean("marked_spam")
        val muting by boolean
        val notificationsEnabled by boolean("notifications_enabled")
        val screenName by string("screen_name")
        val wantRetweets by boolean("want_retweets")
    }

    data class Target(override val json: JsonObject): PenicillinModel {
        val followedBy by boolean("followed_by")
        val following by boolean
        val followingReceived by boolean("following_received")
        val followingRequested by boolean("following_requested")
        val id by long
        val idStr by string("id_str")
        val screenName by string("screen_name")
    }
}

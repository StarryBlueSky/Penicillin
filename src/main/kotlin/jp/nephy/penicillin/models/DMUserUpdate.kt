@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class DMUserUpdate(override val json: ImmutableJsonObject): PenicillinModel {
    private val userEvents by immutableJsonObject("user_events")

    val cursor by userEvents.byString
    val lastSeenEventId by userEvents.byString("last_seen_event_id")
    val trustedLastSeenEventId by userEvents.byString("trusted_last_seen_event_id")
    val untrustedLastSeenEventId by userEvents.byString("untrusted_last_seen_event_id")
}

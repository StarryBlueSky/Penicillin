@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.jsonObject

data class DMUserUpdate(override val json: JsonObject): PenicillinModel {
    private val userEvents by jsonObject("user_events")
    val cursor by userEvents.byString
    val lastSeenEventId by userEvents.byString("last_seen_event_id")
    val trustedLastSeenEventId by userEvents.byString("trusted_last_seen_event_id")
    val untrustedLastSeenEventId by userEvents.byString("untrusted_last_seen_event_id")
}

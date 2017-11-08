package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byObject
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

@Suppress("UNUSED")
class DMUserUpdate(val json: JsonElement) {
    val userEvents by json.byObject("user_events")

    val cursor by json["user_events"].byString
    val lastSeenEventId by json["user_events"].byString("last_seen_event_id")
    val trustedLastSeenEventId by json["user_events"].byString("trusted_last_seen_event_id")
    val untrustedLastSeenEventId by json["user_events"].byString("untrusted_last_seen_event_id")
}

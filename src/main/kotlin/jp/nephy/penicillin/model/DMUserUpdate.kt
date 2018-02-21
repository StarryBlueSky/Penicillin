package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class DMUserUpdate(override val json: JsonObject): JsonModel {
    val userEvents by json.byJsonObject("user_events")

    val cursor by json["user_events"].byString
    val lastSeenEventId by json["user_events"].byString("last_seen_event_id")
    val trustedLastSeenEventId by json["user_events"].byString("trusted_last_seen_event_id")
    val untrustedLastSeenEventId by json["user_events"].byString("untrusted_last_seen_event_id")
}

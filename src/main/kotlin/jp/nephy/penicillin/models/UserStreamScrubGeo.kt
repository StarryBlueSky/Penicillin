package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byLong
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class UserStreamScrubGeo(override val json: JsonObject): PenicillinModel {
    private val scrubGeo by immutableJsonObject("scrub_geo")

    val userId by scrubGeo.byLong("user_id")
    val userIdStr by scrubGeo.byString("user_id_str")
    val upToStatusId by scrubGeo.byLong("up_to_status_id")
    val upToStatusIdStr by scrubGeo.byString("up_to_status_id_str")
    val timestampMs by scrubGeo.byString("timestamp_ms")
}

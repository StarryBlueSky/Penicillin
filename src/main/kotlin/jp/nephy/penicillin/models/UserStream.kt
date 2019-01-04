@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

object UserStream {
    abstract class Event(final override val json: JsonObject): PenicillinModel {
        val event by string
        val source by model<User>()
        val target by model<User>()
        // val createdAt by string("created_at")
    }

    data class UserEvent(val parentJson: JsonObject): Event(parentJson)

    data class StatusEvent(val parentJson: JsonObject): Event(parentJson) {
        val targetObject by model<Status>(key = "target_object")
    }

    data class ListEvent(val parentJson: JsonObject): Event(parentJson) {
        val targetObject by model<TwitterList>(key = "target_object")
    }

    data class Friends(override val json: JsonObject): PenicillinModel {
        val friends by nullableLongList()
        val friendsStr by nullableStringList
    }

    data class ScrubGeo(override val json: JsonObject): PenicillinModel {
        private val scrubGeo by jsonObject("scrub_geo")
        val userId by scrubGeo.byLong("user_id")
        val userIdStr by scrubGeo.byString("user_id_str")
        val upToStatusId by scrubGeo.byLong("up_to_status_id")
        val upToStatusIdStr by scrubGeo.byString("up_to_status_id_str")
        val timestampMs by scrubGeo.byString("timestamp_ms")
    }

    data class StatusWithheld(override val json: JsonObject): PenicillinModel {
        private val statusWithheld by jsonObject("status_withheld")
        val userId by statusWithheld.byLong("user_id")
        val id by statusWithheld.byLong
        val timestampMs by statusWithheld.byString("timestamp_ms")
        val withheldInCountries by statusWithheld.byStringList("withheld_in_countries")
    }

    data class UserWithheld(override val json: JsonObject): PenicillinModel {
        private val userWithheld by jsonObject("user_withheld")
        val id by userWithheld.byLong
        val withheldInCountries by userWithheld.byStringList("withheld_in_countries")
    }

    data class Disconnect(override val json: JsonObject): PenicillinModel {
        private val disconnect by jsonObject
        val code by disconnect.byInt
        val streamName by disconnect.byNullableString("stream_name")
        val reason by disconnect.byString
    }

    data class Limit(override val json: JsonObject): PenicillinModel {
        private val limit by jsonObject
        val track by limit.byInt
        val timestampMs by limit.byString("timestamp_ms")
    }

    data class Warning(override val json: JsonObject): PenicillinModel {
        private val warning by jsonObject
        val code by warning.byInt
        val message by warning.byString
        val percentFull by warning.byNullableInt("percent_full")
        val userId by warning.byNullableLong
    }
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendMetadata(override val json: JsonObject): PenicillinModel {
    val contextMode by string("context_mode")
    val refreshIntervalMillis by int("refresh_interval_millis")
    val timestamp by long
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendMetadata(override val json: ImmutableJsonObject): PenicillinModel {
    val contextMode by string("context_mode")
    val refreshIntervalMillis by int("refresh_interval_millis")
    val timestamp by long
}

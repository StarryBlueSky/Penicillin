@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SleepTime(override val json: JsonObject): PenicillinModel {
    val enabled by boolean
    val startTime by nullableLong("start_time")
    val endTime by nullableLong("end_time")
}

package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byNullableLong


data class SleepTime(override val json: JsonObject): PenicillinModel {
    val enabled by json.byBool
    val startTime by json.byNullableLong("start_time")
    val endTime by json.byNullableLong("end_time")
}

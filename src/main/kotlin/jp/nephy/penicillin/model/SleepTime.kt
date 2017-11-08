package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byNullableLong
import com.google.gson.JsonElement

@Suppress("UNUSED")
class SleepTime(val json: JsonElement) {
    val enabled by json.byBool
    val startTime by json.byNullableLong("start_time")
    val endTime by json.byNullableLong("end_time")
}

package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byNullableLong

@Suppress("UNUSED")
class SleepTime(override val json: JsonObject): JsonModel {
    val enabled by json.byBool
    val startTime by json.byNullableLong("start_time")
    val endTime by json.byNullableLong("end_time")
}

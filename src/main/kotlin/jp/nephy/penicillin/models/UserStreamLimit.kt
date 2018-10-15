package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byInt
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.immutableJsonObject

data class UserStreamLimit(override val json: JsonObject): PenicillinModel {
    val track by json["limit"]!!.immutableJsonObject.byInt
    val timestampMs by json["limit"]!!.immutableJsonObject.byString("timestamp_ms")
}

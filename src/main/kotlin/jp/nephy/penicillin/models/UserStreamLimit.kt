@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byInt
import jp.nephy.jsonkt.delegation.byString

data class UserStreamLimit(override val json: JsonObject): PenicillinModel {
    val track by json["limit"].jsonObject.byInt
    val timestampMs by json["limit"].jsonObject.byString("timestamp_ms")
}

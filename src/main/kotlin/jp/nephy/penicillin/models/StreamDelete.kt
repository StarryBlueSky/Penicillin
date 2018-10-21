@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byImmutableJsonObject
import jp.nephy.jsonkt.delegation.byLong
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class StreamDelete(override val json: ImmutableJsonObject): PenicillinModel {
    private val delete by immutableJsonObject
    private val status by delete.byImmutableJsonObject

    val timestampMs by delete.byString("timestamp_ms")
    val statusId by status.byLong("id")
    val statusIdStr by status.byString("id_str")
    val userId by status.byLong("user_id")
    val userIdStr by status.byString("user_id_str")
}

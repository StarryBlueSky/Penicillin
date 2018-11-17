@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byInt
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.jsonObject

data class UserStreamDisconnect(override val json: JsonObject): PenicillinModel {
    private val disconnect by jsonObject
    val code by disconnect.byInt
    val streamName by disconnect.byNullableString("stream_name")
    val reason by disconnect.byString
}

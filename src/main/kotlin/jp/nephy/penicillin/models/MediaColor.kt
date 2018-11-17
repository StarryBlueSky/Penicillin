@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.jsonObject
import jp.nephy.jsonkt.delegation.int

data class MediaColor(override val json: JsonObject): PenicillinModel {
    val r by jsonObject
    val ttl by int
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObject
import jp.nephy.jsonkt.delegation.int


data class MediaColor(override val json: JsonObject): PenicillinModel {
    val r by immutableJsonObject
    val ttl by int
}

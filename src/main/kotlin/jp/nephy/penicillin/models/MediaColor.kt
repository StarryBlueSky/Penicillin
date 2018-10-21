@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObject
import jp.nephy.jsonkt.delegation.int


data class MediaColor(override val json: ImmutableJsonObject): PenicillinModel {
    val r by immutableJsonObject
    val ttl by int
}

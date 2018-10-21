@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.long

data class ApplicationRateLimit(override val json: ImmutableJsonObject): PenicillinModel {
    val limit by int
    val remaining by int
    val reset by long
}

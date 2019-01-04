@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class Photo(override val json: JsonObject): PenicillinModel {
    val large by model<Size>()
    val medium by model<Size>()
    val small by model<Size>()
    val thumb by model<Size>()

    data class Size(override val json: JsonObject): PenicillinModel {
        val h by int
        val resize by string
        val w by int
    }
}

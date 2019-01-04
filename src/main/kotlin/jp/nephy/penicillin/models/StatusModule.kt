@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class StatusModule(override val json: JsonObject): PenicillinModel {
    val metadata by model<StatusMetadata>()
    val data by model<Status>()

    data class StatusMetadata(override val json: JsonObject): PenicillinModel {
        val resultType by string("result_type")
    }
}

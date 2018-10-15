package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class StatusModule(override val json: JsonObject): PenicillinModel {
    val metadata by model<StatusMetadata>()
    val data by model<Status>()
}

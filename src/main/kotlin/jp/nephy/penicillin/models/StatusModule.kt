@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class StatusModule(override val json: ImmutableJsonObject): PenicillinModel {
    val metadata by model<StatusMetadata>()
    val data by model<Status>()
}

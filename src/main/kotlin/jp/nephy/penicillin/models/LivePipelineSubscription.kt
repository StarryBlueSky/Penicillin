@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.jsonObject

data class LivePipelineSubscription(override val json: JsonObject): PenicillinModel {
    val subscriptions by jsonObject
}

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObject


data class LivePipelineSubscription(override val json: JsonObject): PenicillinModel {
    val subscriptions by immutableJsonObject
}

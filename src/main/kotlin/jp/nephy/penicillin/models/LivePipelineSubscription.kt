@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObject


data class LivePipelineSubscription(override val json: ImmutableJsonObject): PenicillinModel {
    val subscriptions by immutableJsonObject
}

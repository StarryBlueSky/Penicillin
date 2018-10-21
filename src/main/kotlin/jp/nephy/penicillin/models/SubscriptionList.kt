@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class SubscriptionList(override val json: ImmutableJsonObject): PenicillinModel {
    val environment by string
    val applicationId by string("application_id")
    val subscriptions by modelList<Subscription>()

    data class Subscription(override val json: ImmutableJsonObject): PenicillinModel {
        val userId by string("user_id")
    }
}

@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SubscriptionCount(override val json: JsonObject): PenicillinModel {
    val accountName by string("account_name")
    val subscriptionsCountAll by string("subscriptions_count_all")
    val subscriptionsCountDirectMessages by string("subscriptions_count_direct_messages")
}

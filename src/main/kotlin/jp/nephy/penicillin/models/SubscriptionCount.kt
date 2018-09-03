package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString

data class SubscriptionCount(override val json: JsonObject): PenicillinModel {
    val accountName by json.byString("account_name")
    val subscriptionsCountAll by json.byString("subscriptions_count_all")
    val subscriptionsCountDirectMessages by json.byString("subscriptions_count_direct_messages")
}

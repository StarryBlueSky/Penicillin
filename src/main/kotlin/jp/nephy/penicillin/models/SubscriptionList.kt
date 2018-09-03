package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString

data class SubscriptionList(override val json: JsonObject): PenicillinModel {
    val environment by json.byString
    val applicationId by json.byString("application_id")
    val subscriptions by json.byModelList<Subscription>()

    data class Subscription(override val json: JsonObject): PenicillinModel {
        val userId by json.byString("user_id")
    }
}

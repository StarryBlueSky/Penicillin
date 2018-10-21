@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.SubscriptionCount
import jp.nephy.penicillin.models.SubscriptionList
import jp.nephy.penicillin.models.Webhook
import jp.nephy.penicillin.models.WebhookList


class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, envName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account_activity/all/$envName/webhooks.json") {
        body {
            form {
                add("url" to url, *options)
            }
        }
    }.jsonObject<Webhook>()

    fun listWebhooks(vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/webhooks.json") {
        parameter(*options)
    }.jsonObject<WebhookList>()

    fun listWebhooks(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/webhooks.json") {
        parameter(*options)
    }.jsonArray<Webhook>()

    fun triggerCRC(envName: String, webhookId: String, vararg options: Pair<String, Any?>) = client.session.put("/1.1/account_activity/all/$envName/webhooks/$webhookId.json") {
        parameter(*options)
    }.empty()

    fun deleteWebhook(envName: String, webhookId: String, vararg options: Pair<String, Any?>) = client.session.delete("/1.1/account_activity/all/$envName/webhooks/$webhookId.json") {
        parameter(*options)
    }.empty()

    fun subscribe(envName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account_activity/all/$envName/subscriptions.json") {
        body {
            form {
                add(*options)
            }
        }
    }.empty()

    fun subscriptionCount(vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/subscriptions/count.json") {
        parameter(*options)
    }.jsonObject<SubscriptionCount>()

    fun checkSubscription(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/subscriptions.json") {
        parameter(*options)
    }.empty()

    fun listSubscriptions(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/subscriptions/list.json") {
        parameter(*options)
    }.jsonObject<SubscriptionList>()

    fun unsubscribe(envName: String, vararg options: Pair<String, Any?>) = client.session.delete("/1.1/account_activity/all/$envName/subscriptions.json") {
        parameter(*options)
    }.empty()
}

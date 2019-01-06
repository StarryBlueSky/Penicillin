@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Subscription
import jp.nephy.penicillin.models.Webhook

class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, envName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account_activity/all/$envName/webhooks.json") {
        body {
            form {
                add("url" to url, *options)
            }
        }
    }.jsonObject<Webhook.Model>()

    fun listWebhooks(vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/webhooks.json") {
        parameter(*options)
    }.jsonObject<Webhook.List>()

    fun listWebhooks(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/webhooks.json") {
        parameter(*options)
    }.jsonArray<Webhook.Model>()

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
    }.jsonObject<Subscription.Count>()

    fun checkSubscription(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/subscriptions.json") {
        parameter(*options)
    }.empty()

    fun listSubscriptions(envName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/account_activity/all/$envName/subscriptions/list.json") {
        parameter(*options)
    }.jsonObject<Subscription.List>()

    fun unsubscribe(envName: String, vararg options: Pair<String, Any?>) = client.session.delete("/1.1/account_activity/all/$envName/subscriptions.json") {
        parameter(*options)
    }.empty()
}

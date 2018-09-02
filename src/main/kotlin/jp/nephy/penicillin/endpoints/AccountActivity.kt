package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Webhook


class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, envName: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account_activity/$envName/webhooks.json") {
        body {
            form {
                add("url" to url, *options)
            }
        }
    }.jsonObject<Webhook>()
}

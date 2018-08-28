package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient


class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account_activity/all/webhooks.json") {
        body {
            form {
                add("url" to url, *options)
            }
        }
    }.emptyJsonObject()
}

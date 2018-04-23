package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.Empty


class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/account_activity/all/webhooks.json") {
        query("url" to url, *options)
    }
}

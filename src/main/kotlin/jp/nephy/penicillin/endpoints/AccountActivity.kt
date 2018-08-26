package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Empty


class AccountActivity(override val client: PenicillinClient): Endpoint {
    fun registerWebhook(url: String, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/account_activity/all/webhooks.json") {
        form("url" to url, *options)
    }
}

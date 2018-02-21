package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.response.ResponseObject

@Suppress("UNUSED")
class AccountActivity(private val client: Client) {
    @POST
    fun registerWebhook(url: String, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/account_activity/all/webhooks.json")
                .param("url" to url)
                .params(*options)
                .post()
                .getResponseObject()
    }
}

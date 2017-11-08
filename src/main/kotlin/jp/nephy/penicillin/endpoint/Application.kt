package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.model.ApplicationRateLimitStatus
import jp.nephy.penicillin.response.ResponseObject

@Suppress("UNUSED")
class Application(private val client: Client) {
    @GET
    fun getRateLimitStatus(resources: Array<String>?=null, vararg options: Pair<String, String?>): ResponseObject<ApplicationRateLimitStatus> {
        return client.session.new()
                .url("/application/rate_limit_status.json")
                .param("resources" to resources?.joinToString(","))
                .params(*options)
                .get()
                .getResponseObject()
    }
}

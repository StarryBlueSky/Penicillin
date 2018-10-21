@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.ApplicationRateLimitStatus

class Application(override val client: PenicillinClient): Endpoint {
    fun getRateLimitStatus(resources: List<String>? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/application/rate_limit_status.json") {
        parameter("resources" to resources?.joinToString(","), *options)
    }.jsonObject<ApplicationRateLimitStatus>()
}

package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.ApplicationRateLimitStatus

class Application(override val client: PenicillinClient): Endpoint {
    fun getRateLimitStatus(resources: List<String>? = null, vararg options: Pair<String, Any?>)= client.session.getObject<ApplicationRateLimitStatus>("/application/rate_limit_status.json") {
        query("resources" to resources?.joinToString(","), *options)
    }
}

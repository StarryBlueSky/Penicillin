package jp.nephy.penicillin.response

import jp.nephy.penicillin.misc.AccessLevel
import jp.nephy.penicillin.misc.RateLimit
import okhttp3.Request
import okhttp3.Response


abstract class AbstractRestResponse(final override val content: String, final override val request: Request, final override val response: Response): IResult {
    private val responseHeaders = response.headers()

    override val rateLimit = RateLimit(responseHeaders)
    override val accessLevel = AccessLevel.getLevel(responseHeaders)
    override val responseTimeMs = responseHeaders["x-response-time"]?.toIntOrNull()
}

package jp.nephy.penicillin.response

import jp.nephy.penicillin.misc.AccessLevel
import jp.nephy.penicillin.misc.RateLimit
import okhttp3.Request
import okhttp3.Response

@Suppress("UNUSED")
abstract class AbsRESTResponse(val content: String, val request: Request, val response: Response) {
    private val originalHeaders = response.headers()
    val headers: Map<String, List<String>> = originalHeaders.toMultimap()
    val rateLimit = RateLimit(originalHeaders)
    val accessLevel = AccessLevel.getLevel(originalHeaders)
    val responseTimeMs = if (originalHeaders["x-response-time"] != null) {
        originalHeaders["x-response-time"]!!.toInt()
    } else {
        null
    }

    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
        println(content)
        println()
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAtEpoch?.toDate()})")
        println("AccessLevel: $accessLevel")
        println("This request has done in $responseTimeMs ms.")
        println()
    }
}

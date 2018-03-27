package jp.nephy.penicillin.response

import jp.nephy.penicillin.misc.AccessLevel
import jp.nephy.penicillin.misc.RateLimit
import okhttp3.Request
import okhttp3.Response

interface IResult {
    val content: String
    val request: Request
    val response: Response

    val rateLimit: RateLimit
    val accessLevel: AccessLevel
    val responseTimeMs: Int?

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

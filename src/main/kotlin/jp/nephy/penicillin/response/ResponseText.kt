package jp.nephy.penicillin.response

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.misc.RateLimit
import okhttp3.Request
import okhttp3.Response

data class ResponseText(val content: String, val client: Client, val request: Request, val response: Response, val rateLimit: RateLimit) {
    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
        println(content)
        println()
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAtEpoch?.toDate()})")
    }
}
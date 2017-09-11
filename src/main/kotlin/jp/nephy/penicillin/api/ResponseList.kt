package jp.nephy.penicillin.api

import okhttp3.Request
import okhttp3.Response
import java.util.*

data class ResponseList<T>(val content: String, val request: Request, val response: Response, val rateLimit: RateLimit) : ArrayList<T>() {
    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
        println(content)
        println()
        println("Data size: ${this.size}")
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAtEpoch?.toDate()})")
    }
}

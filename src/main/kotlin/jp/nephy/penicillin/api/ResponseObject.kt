package jp.nephy.penicillin.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response

data class ResponseObject<out T>(val data: T, val request: Request, val response: Response, val error: FuelError?, val rateLimit: RateLimit) {
    fun print() {
        println(request.toString())
        println()
        println(response.toString())
        println()
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAt.toDate()})")
    }
}

package jp.nephy.penicillin.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import java.util.*

data class ResponseList<T>(val request: Request, val response: Response, val error: FuelError?, val rateLimit: RateLimit) : ArrayList<T>() {
    fun print() {
        println(request.toString())
        println()
        println(response.toString())
        println()
        println("Data size: ${this.size}")
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAt.toDate()})")
    }
}

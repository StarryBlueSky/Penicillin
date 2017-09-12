package jp.nephy.penicillin.response

import jp.nephy.penicillin.Client
import okhttp3.Request
import okhttp3.Response

data class ResponseStream(val client: Client, val request: Request, val response: Response) {
    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
    }
}

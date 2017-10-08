package jp.nephy.penicillin.response

import jp.nephy.penicillin.streaming.IListener
import okhttp3.Request
import okhttp3.Response

data class ResponseStream<T>(val request: Request, val response: Response) {
    @Suppress("UNCHECKED_CAST")
    fun listen(listener: T) = (listener as IListener<T>).getReceiver(this)

    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
    }
}

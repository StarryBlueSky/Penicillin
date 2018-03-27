package jp.nephy.penicillin.response

import jp.nephy.penicillin.streaming.IListener
import okhttp3.Request
import okhttp3.Response


class ResponseStream<T>(val request: Request, val response: Response) {
    fun listen(listener: IListener<T>) = listener.getReceiver(this)

    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
    }
}

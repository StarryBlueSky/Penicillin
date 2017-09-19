package jp.nephy.penicillin.response

import jp.nephy.penicillin.streaming.*
import okhttp3.Request
import okhttp3.Response

data class ResponseStream(val request: Request, val response: Response) {
    fun listen(listener: IUserStreamListener) = UserStreamReceiver(this, listener)
    fun listen(listener: ISampleStreamListener) = SampleStreamReceiver(this, listener)
    fun listen(listener: IFilterStreamListener) = FilterStreamReceiver(this, listener)
    fun listen(listener: ILivePipelineListener) = LivePipelineReceiver(this, listener)

    fun print() {
        println(request.toString())
        println(request.headers())
        println()
        println(response.toString())
        println(response.headers())
    }
}

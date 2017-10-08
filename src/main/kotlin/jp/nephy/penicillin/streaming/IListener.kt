package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.response.ResponseStream

interface IListener<T> {
    fun getReceiver(response: ResponseStream<T>): AbsStreamingParser<T>
}

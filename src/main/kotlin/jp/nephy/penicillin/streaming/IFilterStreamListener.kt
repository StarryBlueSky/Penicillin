package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.response.ResponseStream

interface IFilterStreamListener: IListener<IFilterStreamListener> {
    override fun getReceiver(response: ResponseStream<IFilterStreamListener>) = FilterStreamReceiver(response, this)

    fun onStatus(status: Status)

    fun onDelete(delete: Delete)

    fun onUnknownData(data: String)
}
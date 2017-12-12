package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.response.ResponseStream

interface ILivePipelineListener: IListener<ILivePipelineListener> {
    override fun getReceiver(response: ResponseStream<ILivePipelineListener>) = LivePipelineReceiver(response, this)

    fun onUpdateLikeCount(id: StatusID, count: Int) {}
    fun onUpdateRetweetCount(id: StatusID, count: Int) {}
    fun onUpdateReplyCount(id: StatusID, count: Int) {}

    fun onUnknownData(data: String) {}
}

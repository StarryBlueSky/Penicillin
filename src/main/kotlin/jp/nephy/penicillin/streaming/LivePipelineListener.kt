package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.util.StatusID

interface LivePipelineListener: StreamListener<LivePipelineHandler> {
    fun onUpdateLikeCount(id: StatusID, count: Int) {}
    fun onUpdateRetweetCount(id: StatusID, count: Int) {}
    fun onUpdateReplyCount(id: StatusID, count: Int) {}
    fun onUnknownData(data: String) {}
}

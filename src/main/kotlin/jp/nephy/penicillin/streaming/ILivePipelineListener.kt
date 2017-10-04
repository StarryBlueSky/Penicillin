package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.misc.StatusID

interface ILivePipelineListener {
    fun onUpdateLikeCount(id: StatusID, count: Int)
    fun onUpdateRetweetCount(id: StatusID, count: Int)
    fun onUpdateReplyCount(id: StatusID, count: Int)

    fun onUnknownData(data: String)
}
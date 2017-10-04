package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.misc.StatusID

class PrintLivePipelineListener : ILivePipelineListener {
    override fun onUpdateLikeCount(id: StatusID, count: Int) {
        println("Status $id: Like -> $count")
    }
    override fun onUpdateRetweetCount(id: StatusID, count: Int) {
        println("Status $id: Retweet -> $count")
    }
    override fun onUpdateReplyCount(id: StatusID, count: Int) {
        println("Status $id: Reply -> $count")
    }

    override fun onUnknownData(data: String) {
        println("Unknown Data: $data")
    }
}

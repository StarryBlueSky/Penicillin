package jp.nephy.penicillin.core.streaming.listener

interface LivePipelineListener: StreamListener {
    suspend fun onUpdateLikeCount(id: Long, count: Int) {}
    suspend fun onUpdateRetweetCount(id: Long, count: Int) {}
    suspend fun onUpdateReplyCount(id: Long, count: Int) {}
}

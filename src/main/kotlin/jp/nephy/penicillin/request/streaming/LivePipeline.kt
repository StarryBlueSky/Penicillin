package jp.nephy.penicillin.request.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.model.special.StatusID
import jp.nephy.penicillin.request.StreamAction

typealias LivePipelineListener = LivePipeline.Listener

class LivePipeline(action: StreamAction<LivePipelineListener>, listener: LivePipelineListener): StreamHandler<LivePipelineListener>(action, listener) {
    override fun handle(json: JsonObject) {
        val topic = json["topic"].string

        if (topic.startsWith("/tweet_engagement/")) {
            val id = StatusID(topic.split("/").last().toLong())
            val engagement = json["payload"]["tweet_engagement"].jsonObject

            when {
                engagement.contains("like_count") -> listener.onUpdateLikeCount(id, engagement["like_count"].int)
                engagement.contains("retweet_count") -> listener.onUpdateRetweetCount(id, engagement["retweet_count"].int)
                engagement.contains("reply_count") -> listener.onUpdateReplyCount(id, engagement["reply_count"].int)
                else -> listener.onUnknownData(json)
            }
        } else {
            listener.onUnknownData(json)
        }
    }

    interface Listener: StreamListener {
        fun onUpdateLikeCount(id: StatusID, count: Int) {}
        fun onUpdateRetweetCount(id: StatusID, count: Int) {}
        fun onUpdateReplyCount(id: StatusID, count: Int) {}
        fun onUnknownData(data: JsonObject) {}
    }
}

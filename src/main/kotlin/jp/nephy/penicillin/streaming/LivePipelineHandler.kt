package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.util.StatusID
import okhttp3.Response

class LivePipelineHandler(response: Response, private val listener: LivePipelineListener): StreamingParser(response, listener) {
    override fun handle(json: JsonObject) {
        val topic = json["topic"].string

        if (topic.startsWith("/tweet_engagement/")) {
            val id = StatusID(topic.split("/").last().toLong())
            val engagement = json["payload"]["tweet_engagement"].jsonObject

            when {
                engagement.contains("like_count") -> listener.onUpdateLikeCount(id, engagement["like_count"].int)
                engagement.contains("retweet_count") -> listener.onUpdateRetweetCount(id, engagement["retweet_count"].int)
                engagement.contains("reply_count") -> listener.onUpdateReplyCount(id, engagement["reply_count"].int)
                else -> listener.onUnknownData(json.toString())
            }
        } else {
            listener.onUnknownData(json.toString())
        }
    }
}

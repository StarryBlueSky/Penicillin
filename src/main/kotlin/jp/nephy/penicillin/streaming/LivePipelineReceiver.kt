package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.jsonkt.get
import jp.nephy.jsonkt.jsonObject
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.response.ResponseStream

class LivePipelineReceiver(response: ResponseStream<ILivePipelineListener>, private val listener: ILivePipelineListener): AbsStreamingParser<ILivePipelineListener>(response) {
    override fun handle(json: JsonObject) {
        val topic = json["topic"].asString

        if (topic.startsWith("/tweet_engagement/")) {
            val id = StatusID(topic.split("/").last().toLong())
            val engagement = json["payload"]["tweet_engagement"]

            when {
                engagement.jsonObject.contains("like_count") -> listener.onUpdateLikeCount(id, engagement["like_count"].asInt)
                engagement.jsonObject.contains("retweet_count") -> listener.onUpdateRetweetCount(id, engagement["retweet_count"].asInt)
                engagement.jsonObject.contains("reply_count") -> listener.onUpdateReplyCount(id, engagement["reply_count"].asInt)
                else -> listener.onUnknownData(json.toString())
            }
        } else {
            listener.onUnknownData(json.toString())
        }
    }
}

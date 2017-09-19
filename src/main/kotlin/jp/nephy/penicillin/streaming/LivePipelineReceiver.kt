package jp.nephy.penicillin.streaming

import com.github.salomonbrys.kotson.contains
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonObject
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.response.ResponseStream

class LivePipelineReceiver(response: ResponseStream, private val listener: ILivePipelineListener): AbsStreamingParser(response) {
    override fun handle(json: JsonObject) {
        val topic = json["topic"].asString

        if (topic.startsWith("/tweet_engagement/")) {
            val id = StatusID(topic.split("/").last().toLong())
            val engagement = json["payload"]["tweet_engagement"]

            if (engagement.obj.contains("like_count")) {
                listener.onUpdateLikeCount(id, engagement["like_count"].asInt)
            } else if (engagement.obj.contains("retweet_count")) {
                listener.onUpdateRetweetCount(id, engagement["retweet_count"].asInt)
            }
        } else {
            listener.onUnknownData(json.toString())
        }
    }
}

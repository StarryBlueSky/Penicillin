package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class LivePipelineHandler(override val listener: LivePipelineListener): StreamHandler<LivePipelineListener> {
    override suspend fun handle(json: JsonObject, context: CoroutineContext) {
        val topic = json.getOrNull("topic").nullableString
        val id = topic?.split("/")?.lastOrNull()?.toLongOrNull()
        val engagement = json.getOrNull("payload")?.nullableJsonObject?.getOrNull("tweet_engagement").nullableJsonObject
        if (topic != null && id != null && engagement != null && topic.startsWith("/tweet_engagement/")) {
            when {
                engagement.contains("like_count") -> launch(context) {
                    listener.onUpdateLikeCount(id, engagement["like_count"].int)
                }
                engagement.contains("retweet_count") -> launch(context) {
                    listener.onUpdateRetweetCount(id, engagement["retweet_count"].int)
                }
                engagement.contains("reply_count") -> launch(context) {
                    listener.onUpdateReplyCount(id, engagement["reply_count"].int)
                }
                else -> launch(context) {
                    listener.onUnhandledData(json)
                }
            }
        } else {
            launch(context) {
                listener.onUnhandledData(json)
            }
        }

        launch(context) {
            listener.onRawJson(json)
        }
    }
}

interface LivePipelineListener: StreamListener {
    suspend fun onUpdateLikeCount(id: Long, count: Int) {}
    suspend fun onUpdateRetweetCount(id: Long, count: Int) {}
    suspend fun onUpdateReplyCount(id: Long, count: Int) {}
}

package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class LivePipelineHandler(override val listener: LivePipelineListener): StreamHandler<LivePipelineListener> {
    override suspend fun handle(json: JsonObject, context: CoroutineContext) {
        launch(context) {
            val topic = json.getOrNull("topic").nullableString?: return@launch listener.onUnhandledJson(json)
            val id = topic.split("/").lastOrNull()?.toLongOrNull() ?: return@launch listener.onUnhandledJson(json)
            val engagement = json.getOrNull("payload")?.nullableJsonObject?.getOrNull("tweet_engagement").nullableJsonObject ?: return@launch listener.onUnhandledJson(json)
            if (topic.startsWith("/tweet_engagement/")) {
                when {
                    engagement.contains("like_count") -> {
                        listener.onUpdateLikeCount(id, engagement["like_count"].nullableInt ?: return@launch)
                    }
                    engagement.contains("retweet_count") -> {
                        listener.onUpdateRetweetCount(id, engagement["retweet_count"].nullableInt ?: return@launch)
                    }
                    engagement.contains("reply_count") -> {
                        listener.onUpdateReplyCount(id, engagement["reply_count"].nullableInt ?: return@launch)
                    }
                    else -> {
                        listener.onUnhandledJson(json)
                    }
                }
            } else {
                listener.onUnhandledJson(json)
            }
        }

        listener.onAnyJson(json)
    }
}

interface LivePipelineListener: StreamListener {
    suspend fun onUpdateLikeCount(id: Long, count: Int) {}
    suspend fun onUpdateRetweetCount(id: Long, count: Int) {}
    suspend fun onUpdateReplyCount(id: Long, count: Int) {}
}

package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.byNullableImmutableJsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LivePipelineHandler(override val listener: LivePipelineListener): StreamHandler<LivePipelineListener> {
    @Suppress("DEPRECATED_SMARTCAST")
    override suspend fun handle(json: ImmutableJsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            val topic = json.getOrNull("topic")?.nullableString ?: return@launch listener.onUnhandledJson(json)

            val id = topic.split("/").lastOrNull()?.toLongOrNull() ?: return@launch listener.onUnhandledJson(json)
            val payload by json.byNullableImmutableJsonObject
            val engagement = payload?.get("tweet_engagement")?.nullableImmutableJsonObject ?: return@launch listener.onUnhandledJson(json)
            if (topic.startsWith("/tweet_engagement/")) {
                when {
                    "like_count" in engagement -> {
                        listener.onUpdateLikeCount(id, engagement["like_count"].nullableInt ?: return@launch listener.onUnhandledJson(json))
                    }
                    "retweet_count" in engagement -> {
                        listener.onUpdateRetweetCount(id, engagement["retweet_count"].nullableInt ?: return@launch listener.onUnhandledJson(json))
                    }
                    "reply_count" in engagement -> {
                        listener.onUpdateReplyCount(id, engagement["reply_count"].nullableInt ?: return@launch listener.onUnhandledJson(json))
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

package jp.nephy.penicillin.core.streaming.handler

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byNullableJsonObject
import jp.nephy.jsonkt.jsonObjectOrNull
import jp.nephy.jsonkt.stringOrNull
import jp.nephy.penicillin.core.streaming.listener.LivePipelineListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.intOrNull

class LivePipelineHandler(override val listener: LivePipelineListener): StreamHandler<LivePipelineListener> {
    @Suppress("DEPRECATED_SMARTCAST")
    override suspend fun handle(json: JsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            val topic = json.getOrNull("topic")?.stringOrNull ?: return@launch listener.onUnhandledJson(json)
            val id = topic.split("/").lastOrNull()?.toLongOrNull() ?: return@launch listener.onUnhandledJson(json)
            val payload by json.byNullableJsonObject
            val engagement = payload?.get("tweet_engagement")?.jsonObjectOrNull ?: return@launch listener.onUnhandledJson(json)
            if (topic.startsWith("/tweet_engagement/")) {
                when {
                    "like_count" in engagement -> {
                        listener.onUpdateLikeCount(id, engagement["like_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
                    }
                    "retweet_count" in engagement -> {
                        listener.onUpdateRetweetCount(id, engagement["retweet_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
                    }
                    "reply_count" in engagement -> {
                        listener.onUpdateReplyCount(id, engagement["reply_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
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

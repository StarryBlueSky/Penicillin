package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.streaming.LivePipelineHandler
import jp.nephy.penicillin.core.streaming.LivePipelineListener
import jp.nephy.penicillin.models.LivePipelineSubscription


class LivePipeline(override val client: PenicillinClient): Endpoint {
    fun event(topic: List<Long>, vararg options: Pair<String, Any?>) = client.session.get("/1.1/live_pipeline/events") {
        parameter("topic" to topic.joinToString(",") { "/tweet_engagement/$it" }, *options)
    }.stream<LivePipelineListener, LivePipelineHandler>()

    fun update(ids: List<Long>, vararg options: Pair<String, Any?>) = client.session.post("/1.1/live_pipeline/update_subscriptions") {
        body {
            form {
                add("sub_topics" to ids.joinToString(",") { "/tweet_engagement/$it" }, *options)
            }
        }
    }.jsonObject<LivePipelineSubscription>()
}

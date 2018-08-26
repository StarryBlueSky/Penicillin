package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.LivePipelineSubscription
import jp.nephy.penicillin.request.streaming.LivePipelineListener


class LivePipeline(override val client: PenicillinClient): Endpoint {
    fun event(topic: List<Long>, vararg options: Pair<String, Any?>)= client.session.getStream<LivePipelineListener>("https://api.twitter.com/1.1/live_pipeline/events") {
        query("topic" to topic.joinToString(",") { "/tweet_engagement/$it" }, *options)
    }

    fun update(ids: List<Long>, vararg options: Pair<String, Any?>)= client.session.postObject<LivePipelineSubscription>("/live_pipeline/update_subscriptions") {
        form("sub_topics" to ids.joinToString(",") { "/tweet_engagement/$it" }, *options)
    }
}

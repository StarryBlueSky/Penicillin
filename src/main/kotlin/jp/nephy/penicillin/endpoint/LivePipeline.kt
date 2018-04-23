package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.LivePipelineSubscription
import jp.nephy.penicillin.streaming.LivePipelineHandler


class LivePipeline(override val client: PenicillinClient): Endpoint {
    fun event(topic: List<Long>, vararg options: Pair<String, Any?>)= client.session.getStream<LivePipelineHandler>("https://api.twitter.com/1.1/live_pipeline/events") {
        query("topic" to topic.joinToString(",") { "/tweet_engagement/$it" }, *options)
    }

    fun update(ids: List<Long>, vararg options: Pair<String, Any?>)= client.session.postObject<LivePipelineSubscription>("/live_pipeline/update_subscriptions") {
        query("sub_topics" to ids.joinToString(",") { "/tweet_engagement/$it" }, *options)
    }
}

package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.CollectionCreationTimelineOrder
import jp.nephy.penicillin.model.Empty


class Collection(override val client: PenicillinClient): Endpoint {
    fun show(id: String, vararg options: Pair<String, Any?>)= client.session.getObject<Empty>("/collections/show.json") {
        query("id" to id, *options)
    }

    fun list(userId: Long? = null, screenName: String? = null, tweetId: Long? = null, count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Empty>("/collections/list.json") {
        query("user_id" to userId, "screen_name" to screenName, "tweet_id" to tweetId, "count" to count, *options)
    }

    fun create(name: String, description: String? = null, url: String? = null, timelineOrder: CollectionCreationTimelineOrder? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/create.json") {
        form("name" to name, "description" to description, "url" to url, "timeline_order" to timelineOrder?.value, *options)
    }

    fun destroy(id: String, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/destroy.json") {
        form("id" to id, *options)
    }

    fun update(id: String, name: String? = null, description: String? = null, url: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/update.json") {
        form("id" to id, "name" to name, "description" to description, "url" to url, *options)
    }
}
